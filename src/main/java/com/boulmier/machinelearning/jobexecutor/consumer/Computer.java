/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.consumer;

import java.util.HashMap;

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
    
    public void compute(){
       if(sub != null) sub.compute();
    }
    
    private String getData(){
        return data;
    }

    private ComputeProperties getProperties() {
        return properties;
    }

    public static class ComputeProperties {
        public static enum PropertieName {
             
            FILENAME,  //used in storage computer
            EMAIL,     //used in sender computer
            CONTAINER, //used in swift storage (next version with swift)
            ENCODING,
            JOBID     //used in all kind of compute methode
            //...
            
        }
        private final HashMap<PropertieName,String> dbproperties = new HashMap<>();
        
        /**
         * add a new properties (erase the previous one)
         * @param id
         * @param value 
         */
        public void addProperties(PropertieName id, String value){
            dbproperties.put(id, value);
        }
        
        public String getPropertieValue(PropertieName id){
            switch(id){
                case ENCODING:
                    return dbproperties.getOrDefault(id, "UTF-8");
            }
            return dbproperties.get(id);
        }
    }
}
