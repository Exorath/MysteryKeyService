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

package com.exorath.service.mysteryKey.service;

import com.exorath.service.mysteryKey.Service;
import com.exorath.service.mysteryKey.res.AddFragmentsRes;
import com.exorath.service.mysteryKey.res.AddKeysSuccess;
import com.exorath.service.mysteryKey.res.KeyPlayer;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Updates.inc;

/**
 * Created by toonsev on 5/19/2017.
 */
public class MongoService implements Service {
    private MongoCollection<Document> accountsCollection;

    public MongoService(MongoClient client, String databaseName) {
        accountsCollection = client.getDatabase(databaseName).getCollection("accounts");
    }

    public KeyPlayer getPlayer(String uuid) {
        Document document = accountsCollection.find(new Document("_id", uuid)).first();
        if(document == null)
            return new KeyPlayer();
        return playerFromDoc(document);
    }

    public AddFragmentsRes addFragments(String uuid, int amount) {
        try {
            Document document = accountsCollection.findOneAndUpdate(new Document("_id", uuid), inc("fragments", amount), new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
            if (document == null)
                return new AddFragmentsRes(-1, "Query did not return an account");
            return new AddFragmentsRes(playerFromDoc(document));
        }catch (Exception e){
            e.printStackTrace();
            return new AddFragmentsRes(-1, e.getMessage());
        }
    }
    private KeyPlayer playerFromDoc(Document doc){
        return new KeyPlayer(doc.getInteger("keys", 0), doc.getInteger("fragments", 0));
    }


    public AddKeysSuccess addKeys(String uuid, int amount) {
        try {
            gte("keys", 10);
            Document query = new Document("_id", uuid);
            if(amount < 0)
                query.append("keys", new Document("$gte", amount));
            Document document = accountsCollection.findOneAndUpdate(query, inc("keys", amount), new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
            if (document == null)
                return new AddKeysSuccess(-1, "Not enough keys");
            return new AddKeysSuccess(playerFromDoc(document));
        }catch (Exception e){
            e.printStackTrace();
            return new AddKeysSuccess(-1, e.getMessage());
        }
    }
}
