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

            //MONGO IP OPT
            public final String LONGMONGOIPFIELD = "mongodb-ip",
                    SHORTMONGOIPFIELD = "ip",
                    MONGOIPDESCRIPTION = "Define the ip of the mongo server where we want to log the events";
            public final boolean ISMONGOIPREQUIERED = false;

            public final String LONGMONGOPORTFIELD = "mongodb-port",
                    SHORTMONGOPORTFIELD = "port",
                    MONGOPORTDESCRIPTION = "Define the port of the mongo server where we want to log the events";
            public final boolean ISMONGOPORTREQUIERED = false;

            //DEBUG OPT
            public final String LONGDEBUGFIELD = "debug",
                    SHORTDEBUGFIELD = "debug",
                    DEBUGDESCRIPTION = "Enable the debug mode";
        }

        public interface LOGGING {

            public static String MONGO_DEFAULT_IP = "192.168.1.3";
            public static int MONGO_DEFAULT_PORT = 27017;
            public static String MONGO_DEFAULT_DATABASE = "NeuralNetworkSystem";
            public static String MONGO_DEFAULT_COLLECTION = "log";

        }
    }

}
