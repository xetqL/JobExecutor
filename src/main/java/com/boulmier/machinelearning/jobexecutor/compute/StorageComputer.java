/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.compute;

import com.boulmier.machinelearning.jobexecutor.compute.Computer.ComputeProperties.PropertyName;
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
                        properties.getPropertyValue(PropertyName.FILENAME)), 
                        properties.getPropertyValue(PropertyName.ENCODING)))
                ) {
            writer.write(data);
        } catch (IOException ex) {}
    }

}
