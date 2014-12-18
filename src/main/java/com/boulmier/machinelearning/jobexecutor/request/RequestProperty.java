/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.request;

/**
 *
 * @author anthob
 */
public enum RequestProperty {
    
    EXECUTABLE_NAME("null"),
    JOB_NAME("null"),
    JOB_IDENTIFIER("null"),
    NUMBER_OF_HIDDEN_NEURONS("-h"),
    NUMBER_OF_INPUT_NEURONS("-i"),
    TRAINING_SET_URI("-tr"),
    TESTING_SET_URI("-te"),
    NUMBER_OF_EPOCHS("-e");
    private final String cl_opt;
    public static final String NULL = "null";
    private RequestProperty(String cl_opt) {
        this.cl_opt = cl_opt;
    }

    @Override
    public String toString() {
        return cl_opt;
    }
    
    public boolean equalsOpt(Object value){
        if(value instanceof String)
            return ((String) value).equals(cl_opt);
        return false;
    }
    
}
