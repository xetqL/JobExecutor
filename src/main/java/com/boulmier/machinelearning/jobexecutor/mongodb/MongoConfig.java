/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.mongodb;

import com.boulmier.machinelearning.jobexecutor.config.JobExecutorConfig;
import java.util.HashMap;

/**
 *
 * @author Boulmier
 */
public class MongoConfig extends HashMap<String,String>{

    public class MongoOption {

        private final String name;
        private final String value;

        public MongoOption(String name, String value) {
            this.name = name;
            this.value = value;
        }

    }
    public static final String 
            IP_FIELD                    = "ip",
            PORT_FIELD                  = "port",
            DEFAULT_COLLECTION_FIELD    = "schema";

    private MongoConfig() {
        this.put(DEFAULT_COLLECTION_FIELD, 
                JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_COLLECTION);
        this.put(IP_FIELD, 
                JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_IP);
        this.put(PORT_FIELD, 
                JobExecutorConfig.OPTIONS.LOGGING.MONGO_DEFAULT_PORT);
    }

    public static MongoConfig build() {
        return new MongoConfig();
    }

    public MongoConfig addOption(String name, String value) {
        this.put(name, value);
        return this;
    }

    public MongoConfig addOption(MongoOption mo) {
        this.put(mo.name, mo.value);
        return this;
    }
}
