/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.compute;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.request.RequestProperty;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author antho
 */
public class StorageComputer extends Computer {
    private final String DEFAULT_ENCODING_MODE = "UTF-8";
    
    public StorageComputer(Computer subComputer) {
        super(subComputer);
    }

    public StorageComputer(String data, ComputeProperties properties) {
        super(data, properties);
    }

    @Override
    public void compute() {
        super.compute();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(
                        properties.getProperty(RequestProperty.JOB_IDENTIFIER)), 
                        this.DEFAULT_ENCODING_MODE))
                ) {
            writer.write(data);
        } catch (IOException ex) {
            JobExecutor.logger.error("cannot save data from "+properties.getProperty(RequestProperty.JOB_IDENTIFIER)+" to disk");
        }
    }

}
