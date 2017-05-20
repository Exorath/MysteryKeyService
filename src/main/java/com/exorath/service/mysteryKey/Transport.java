/*
 * Copyright 2017 Exorath
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.exorath.service.mysteryKey;

import com.exorath.service.commons.portProvider.PortProvider;
import com.exorath.service.mysteryKey.res.AddFragmentsRes;
import com.exorath.service.mysteryKey.res.AddKeysSuccess;
import com.google.gson.Gson;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

/**
 * Created by toonsev on 5/20/2017.
 */
public class Transport {
    private static final Gson GSON = new Gson();

    public static void setup(Service service, PortProvider portProvider) {
        port(portProvider.getPort());

        get("/players/:uuid", getGetPlayerRoute(service), GSON::toJson);
        post("/players/:uuid/fragments", getIncrementFragmentsRoute(service), GSON::toJson);
        post("/players/:uuid/keys", getIncrementKeysRoute(service), GSON::toJson);
    }

    private static Route getGetPlayerRoute(Service service) {
        return (req, res) -> service.getPlayer(req.params("uuid"));
    }

    private static Route getIncrementFragmentsRoute(Service service) {
        return (req, res) -> {
            try {
                int amount = req.queryParams().contains("amount") ? Integer.valueOf(req.queryParams("amount")) : 1;
                return service.addFragments(req.params("uuid"), amount);
            } catch (Exception e) {
                return new AddFragmentsRes(-1, e.getMessage());
            }
        };
    }

    private static Route getIncrementKeysRoute(Service service) {
        return (req, res) -> {
            try {
                int amount = req.queryParams().contains("amount") ? Integer.valueOf(req.queryParams("amount")) : -1;
                return service.addFragments(req.params("uuid"), amount);
            } catch (Exception e) {
                return new AddKeysSuccess(-1, e.getMessage());
            }
        };
    }
}
