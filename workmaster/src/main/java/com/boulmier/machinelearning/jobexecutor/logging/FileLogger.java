/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import com.bachelor.boulmier.workmaster.WorkMaster;
import java.io.IOException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author Boulmier
 */
public class FileLogger extends ILogger {

    static final String file = WorkMaster.sysMon.osName().matches(".*(Ubuntu|Debian|Linux|).*")? "./JobExecutor.log" : "JobExecutor.log";

    private FileLogger(FileAppender fApp) {
        super(fApp);
    }

    public static FileLogger getLoggerInstance() {
        try {
            FileAppender fApp = new FileAppender(new PatternLayout(ILogger.DEFAULT_PATTERN), FileLogger.file);
            fApp.setThreshold(Level.INFO);
            fApp.setAppend(false);
            return new FileLogger(fApp);
        } catch (IOException ex) {
            return null;
        }
    }
}
