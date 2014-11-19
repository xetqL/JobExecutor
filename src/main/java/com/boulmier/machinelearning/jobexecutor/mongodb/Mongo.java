/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.mongodb;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.jobexecutor.config.JobExecutorConfig;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoTimeoutException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 *
 * @author Boulmier
 */
public class Mongo {

    private static Mongo instance;
    private final MongoClient dbclient;

    private Mongo(MongoClient client) {
        dbclient = client;
    }

    public static Mongo getInstance(MongoConfig wrapper) throws UnknownHostException {
        if (instance == null) {
            StringBuilder host = new StringBuilder();
            MongoClient client;
            for (Map.Entry<String, String> param : wrapper.entrySet()) {
                switch (param.getKey()) {
                    case MongoConfig.IP_FIELD:
                        host.append(param.getValue());
                        break;
                    case MongoConfig.PORT_FIELD:
                        host.append(':').append(param.getValue());
                        break;
                }
            }
            
            client = new MongoClient(host.toString(), MongoClientOptions.builder().connectTimeout(100).build());//new MongoClient(ip, port);
            try {
                client.getDB(JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_DATABASE).command("ping");
                instance = new Mongo(client);
                JobExecutor.logger.info("mongodb accessible at "+host);
            } catch (MongoTimeoutException e) {
                throw new UnknownHostException();
            }
        }
        return instance;
    }

    public DB getDatabase() {
        return dbclient.getDB(JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_DATABASE);
    }

    public DBCollection getLoggingCollection() {
        return getDatabase().getCollection(JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_COLLECTION);
    }

}
