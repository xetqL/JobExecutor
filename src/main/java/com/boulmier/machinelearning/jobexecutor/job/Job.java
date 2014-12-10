/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boulmier.machinelearning.jobexecutor.job;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.jobexecutor.consumer.Computer;
import com.boulmier.machinelearning.jobexecutor.consumer.Computer.ComputeProperties.PropertieName;
import com.boulmier.machinelearning.jobexecutor.consumer.SenderComputer;
import com.boulmier.machinelearning.jobexecutor.consumer.StorageComputer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    protected String jobid;
    protected CommandLine cl;
    protected String executableName;

    public Job(HashMap<String,String> params, String id) {
        this.cl     = generateCommandLine(params);
        this.jobid  = id;
    }
    
    public void start() throws IOException {
        final ByteArrayOutputStream out= new ByteArrayOutputStream();
        final DefaultExecutor exec     = new DefaultExecutor();
        final ExecuteWatchdog wd       = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
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
                    Computer.ComputeProperties properties = new Computer.ComputeProperties();
                    properties.addProperties(PropertieName.FILENAME, "");
                    new SenderComputer( new StorageComputer( out.toString(), properties ) ).compute();
                    JobExecutor.logger.info("Job is complete " + jobid);
                } catch (InterruptedException ex) {
                    exec.getWatchdog().destroyProcess();
                    JobExecutor.logger.error("Job ("+jobid+") has been destroyed due to internal error");
                }
            }
            
        }).start();
        
    }
    
    protected final CommandLine generateCommandLine(HashMap<String,String> params){
        StringBuilder argumentBuilder;
        this.executableName = params.get(JobConfig.EXECUTABLE);
        params.remove(JobConfig.EXECUTABLE);
        this.cl = new CommandLine(executableName);
        for(Map.Entry<String,String> entry : params.entrySet()){
            argumentBuilder = new StringBuilder()
                    .append(entry.getKey())
                    .append(" ")
                    .append(entry.getValue());
            this.cl.addArgument(argumentBuilder.toString());
        }
        return cl;
    }
    
}
