/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;

/**
 *
 * @author Boulmier
 */
public class LoggerFactory {

    private static ILogger logger = null;
    private static ILogger debugger = null;
    public static ILogger getLogger(){
        if(logger == null){
            if(JobExecutor.debugState){
                debugger = ConsoleLogger.getLoggerInstance();
                logger = debugger;
                logger.debug("Debugging mode activated successfully ! ");
            }else{
                if(logger == null){
                    logger = MongoLogger.getLoggerInstance();
                    if(logger == null){
                        logger = FileLogger.getLoggerInstance();
                    }
                    if(logger == null){
                        logger = debugger;
                        logger.error("Mongo unavailable logging into console");
                    } else {
                        logger.error("Mongo unavailable logging into file");
                    }
                }
            }
        }
        return logger;
    }
}
