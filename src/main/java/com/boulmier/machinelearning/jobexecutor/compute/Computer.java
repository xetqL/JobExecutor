/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.compute;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.request.Property;
import com.boulmier.machinelearning.request.Request;
import java.util.Map;

/**
 *
 * @author antho
 */
public abstract class Computer {

    protected Computer sub = null;
    protected final String data;
    protected final ComputeProperties properties;

    public Computer(Computer subComputer) {
        sub = subComputer;
        data = sub.getData();
        properties = sub.getProperties();
    }

    public Computer(String data, ComputeProperties properties) {
        this.data = data;
        this.properties = properties;
    }

    public void compute() {
        if (sub != null) {
            sub.compute();
        }
    }

    private String getData() {
        return data;
    }

    private ComputeProperties getProperties() {
        return properties;
    }

    public static class ComputeProperties {

        private final Map<Property, String> dbproperties;

        private ComputeProperties(Map<Property,String> setup) {
            dbproperties = setup;
        }

        public static ComputeProperties buildFromRequest(Request req) {
            return new ComputeProperties(req.extractSetup());
        }

        public String getProperty(Property id) {

            try{
                String ret = dbproperties.get(id);
                if(ret == null) throw new UnspecifiedPropertyException();
                return ret;
            }catch(UnspecifiedPropertyException un){
                JobExecutor.logger.error(un.getMessage());
            }
            return null;
        }
    }
}
