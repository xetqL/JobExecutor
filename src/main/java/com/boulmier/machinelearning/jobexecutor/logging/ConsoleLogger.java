/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import java.io.IOException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author Boulmier
 */
public class ConsoleLogger extends ILogger{

    public ConsoleLogger() throws IOException {
        super(new ConsoleAppender(new PatternLayout(ILogger.DEFAULT_PATTERN), "System.err"));
    }
    
}
