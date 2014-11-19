/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Boulmier
 */
public abstract class ILogger {

    static final String DEFAULT_PATTERN = "%d [%-5p] %m%n";
    public final Logger log = Logger.getLogger(JobExecutor.class);
    
    public ILogger(AppenderSkeleton app){        
        log.addAppender(app);
        log.setLevel(Level.ALL);
        log.setAdditivity(false);
    }

    public void log(Level lvl, String message) {
        switch (lvl.toInt()) {
            case Level.DEBUG_INT:
                log.debug(message);
                break;
            case Level.ERROR_INT:
                error(message);
                break;
            case Level.WARN_INT:
                warn(message);
                break;
            case Level.FATAL_INT:
                fatal(message);
                break;
            case Level.INFO_INT:
                info(message);
                break;
        }
    }

    public void info(String message) {
        log.info(message);
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void error(String message) {
        log.error(message);
    }

    public void fatal(String message) {
        log.fatal(message);
    }
    
}
