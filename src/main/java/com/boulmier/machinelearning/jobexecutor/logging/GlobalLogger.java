/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import java.io.IOException;

/**
 *
 * @author Boulmier
 */
public class GlobalLogger {
    private static ILogger logger = null;

    public static ILogger getLogger() throws IOException {
        if(logger == null)
            logger = MongoLogger.getLoggerInstance();
        if(logger == null){
            logger = new ConsoleLogger();
            logger.error("Mongo unavailable logging into Console");
        }
        if(logger == null){
            logger = new FileLogger();
            logger.error("Mongo unavailable logging into File");
        }
        return logger;
    }
    
    
    
}
