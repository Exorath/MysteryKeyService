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

package com.exorath.service.mysteryKey.api;

import com.exorath.service.mysteryKey.Service;
import com.exorath.service.mysteryKey.res.AddFragmentsRes;
import com.exorath.service.mysteryKey.res.AddKeysSuccess;
import com.exorath.service.mysteryKey.res.KeyPlayer;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by toonsev on 5/20/2017.
 */
public class MysteryKeyServiceAPI implements Service {
    private static final Gson GSON = new Gson();
    private String address;

    public MysteryKeyServiceAPI(String address) {
        this.address = address;
    }

    @Override
    public KeyPlayer getPlayer(String uuid) {
        try {
            return GSON.fromJson(Unirest.get(url("/players/{uuid}")).routeParam("uuid", uuid).asString().getBody(), KeyPlayer.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AddFragmentsRes addFragments(String uuid, int amount) {
        try {
            return GSON.fromJson(Unirest.post(url("/players/{uuid}/fragments")).routeParam("uuid", uuid).queryString("amount", amount).asString().getBody(), AddFragmentsRes.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            return new AddFragmentsRes(-1, e.getMessage());
        }
    }

    @Override
    public AddKeysSuccess addKeys(String uuid, int amount) {
        try {
            return GSON.fromJson(Unirest.post(url("/players/{uuid}/keys")).routeParam("uuid", uuid).queryString("amount", amount).asString().getBody(), AddKeysSuccess.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            return new AddKeysSuccess(-1, e.getMessage());
        }
    }

    private String url(String endpoint){
        return address + endpoint;
    }
}
