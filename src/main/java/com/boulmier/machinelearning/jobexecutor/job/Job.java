/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boulmier.machinelearning.jobexecutor.job;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author antho
 */
public abstract class Job {

    protected  String jobid;
    protected CommandLine cl;
    
    public void start() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final DefaultExecutor exec = new DefaultExecutor();
        final ExecuteWatchdog wd = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
        final PumpStreamHandler output = new PumpStreamHandler(out);
        final DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
        
        exec.setWatchdog(wd);
        exec.setStreamHandler(output);
        exec.execute(cl,handler);
        
        JobExecutor.logger.info("Running job " + jobid);
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    
                    handler.waitFor();
                    
                    JobExecutor.logger.info("Job is complete " + jobid);
                } catch (InterruptedException ex) {
                    exec.getWatchdog().destroyProcess();
                    JobExecutor.logger.error("Job ("+jobid+") has been destroyed due to internal error");
                }
            }
            
        }).start();
        
    }
    
    protected abstract CommandLine generateCommandLine(HashMap<String,String> params);
    
}
