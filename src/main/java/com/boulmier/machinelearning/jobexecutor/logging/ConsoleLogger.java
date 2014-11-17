/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.logging;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.varia.LevelRangeFilter;

/**
 *
 * @author Boulmier
 */
public class ConsoleLogger extends ILogger{

    private ConsoleLogger(ConsoleAppender cApp){
        
        super( cApp );
        
    }
    
    public static ConsoleLogger getLoggerInstance(){
        ConsoleAppender cApp = new ConsoleAppender(new PatternLayout(ILogger.DEFAULT_PATTERN), "System.err") ;
        cApp.setThreshold(Level.DEBUG);
        LevelRangeFilter lrf = new LevelRangeFilter();
        lrf.setLevelMax(Level.DEBUG);
        lrf.setLevelMin(Level.DEBUG);
        cApp.addFilter(lrf);
        return new ConsoleLogger(cApp);
    }
}
