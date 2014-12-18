/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.compute;

import com.boulmier.machinelearning.request.Request;
import com.boulmier.machinelearning.request.RequestProperty;
import java.util.HashMap;
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

        private final Map<RequestProperty, String> dbproperties;

        private ComputeProperties(Map<RequestProperty,String> setup) {
            dbproperties = setup;
        }

        public static ComputeProperties buildFromRequest(Request req) {
            return new ComputeProperties(req.extractSetup());
        }

        public String getProperty(RequestProperty id) {
            assert (dbproperties.containsKey(id));
            return dbproperties.get(id);
        }
    }
}
