/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.config;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Boulmier
 */
public interface JobExecutorConfig {

    public interface OPTIONS {

        public interface CMD {
            //IP ARG
            public final String SHORTIPFIELD = "ip",
                    LONGIPFIELD = "server-ip",
                    IPDESCRIPTION = "The master external ip where we want to connect.";
            public final boolean ISIPREQUIERED = true;
            
            //PORT ARG
            public final String SHORTPORTFIELD = "port",
                    LONGPORTFIELD = "server-port",
                    PORTDESCRIPTION = "The master port where we want to listen.";
            public final boolean ISPORTREQUIERED = true;
            public final Class PORTTYPE = Integer.class;
            
            //MONGO OPT
            public final String LONGMONGOFIELD = "mongodb-ip",
                    SHORTMONGOFIELD = "ip",
                    MONGODESCRIPTION = "Define the ip of the mongo server where we want to log the events";
            public final boolean ISMONGOREQUIERED = false;
      
        }

        public abstract class LOGGING {
            public static String MONGO_DEFAULT_IP        = "192.168.1.3";
            public static String MONGO_DEFAULT_PORT      = "27017";
            public static String MONGO_DEFAULT_DATABASE  = "NeuralNetworkSystem";
            public static String MONGO_DEFAULT_COLLECTION= "log";
            public static final Map<Integer, String> priority = new TreeMap<>();
            static {
                priority.put(0, "mongodb");
                priority.put(1, "intern");
            }

        }
    }

}
