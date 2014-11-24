/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import com.bachelor.boulmier.workmaster.WorkMaster;

/**
 *
 * @author Boulmier
 */
public class LoggerFactory {

    private static ILogger logger = null;
    private static ILogger debugger = null;

    public static ILogger getLogger() {
        if (logger == null) {
            if (WorkMaster.debug) {
                debugger = ConsoleLogger.getLoggerInstance();
                logger = debugger;
                logger.log.debug("Debugging mode activated successfully ! ");
            } else {
                if (logger == null) {
                    logger = FileLogger.getLoggerInstance();

                    if (logger == null) {
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
