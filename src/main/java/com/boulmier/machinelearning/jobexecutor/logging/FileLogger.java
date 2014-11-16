/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import java.io.IOException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author Boulmier
 */
public class FileLogger extends ILogger{
    static final String file = "~/JobExecutor.log";
    public FileLogger() throws IOException {
        super(new FileAppender(new PatternLayout(ILogger.DEFAULT_PATTERN), FileLogger.file));
    }
}
