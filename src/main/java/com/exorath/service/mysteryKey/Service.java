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

import com.exorath.service.mysteryKey.res.AddFragmentsRes;
import com.exorath.service.mysteryKey.res.AddKeysSuccess;
import com.exorath.service.mysteryKey.res.KeyPlayer;

/**
 * Created by toonsev on 5/19/2017.
 */
public interface Service {
    KeyPlayer getPlayer(String uuid);

    /**
     * If amount if negative, this will only succeed if sufficient are present
     *
     * @param uuid
     * @param amount
     * @return
     */
    AddFragmentsRes addFragments(String uuid, int amount);

    /**
     * If amount if negative, this will only succeed if sufficient are present
     *
     * @param uuid
     * @param amount
     * @return
     */
    AddKeysSuccess addKeys(String uuid, int amount);
}
