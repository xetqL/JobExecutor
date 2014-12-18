/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.job;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.jobexecutor.compute.Computer;
import com.boulmier.machinelearning.jobexecutor.compute.SenderComputer;
import com.boulmier.machinelearning.jobexecutor.compute.StorageComputer;
import com.boulmier.machinelearning.request.Request;
import com.boulmier.machinelearning.request.RequestProperty;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    protected Request req;

    public Job(Request params, String id) {
        this.cl = generateCommandLine(params);
        this.jobid = id;
        this.req = params;
    }

    public void start() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final DefaultExecutor exec = new DefaultExecutor();
        final ExecuteWatchdog wd = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
        final PumpStreamHandler output = new PumpStreamHandler(out);
        final DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();

        exec.setWatchdog(wd);
        exec.setStreamHandler(output);
        exec.execute(cl, handler);

        JobExecutor.logger.info("Running job " + jobid);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    handler.waitFor();
                    Computer.ComputeProperties properties = Computer.ComputeProperties.buildFromRequest(req);
                    new SenderComputer(new StorageComputer(out.toString(), properties)).compute();
                    JobExecutor.logger.info("Job is complete " + jobid);
                } catch (InterruptedException ex) {
                    exec.getWatchdog().destroyProcess();
                    JobExecutor.logger.error("Job (" + jobid + ") has been destroyed due to internal error "+ex.getMessage());
                }
            }

        }).start();

    }

    private CommandLine generateCommandLine(Request req) {
        StringBuilder argumentBuilder;
        this.executableName = req.getExecutableName();
        this.cl = new CommandLine(executableName);
        for (Map.Entry<RequestProperty, String> entry : req) {
            if (!entry.getKey().equalsOpt(RequestProperty.NULL)) {
                argumentBuilder = new StringBuilder()
                        .append(entry.getKey())
                        .append(" ")
                        .append(entry.getValue());
                this.cl.addArgument(argumentBuilder.toString());
            }
        }
        return cl;
    }

}
