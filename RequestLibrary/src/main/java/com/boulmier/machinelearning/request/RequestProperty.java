/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.request;

import java.io.Serializable;

/**
 * Every property with null value should be for Request init
 *
 * @author anthob
 */
public abstract class RequestProperty implements Serializable {

    private static final String NULL = "null";
    public static final Property<String, String> EXECUTABLE_NAME = new Property<>("executable", NULL),
            JOB_NAME = new Property<>("job_name", NULL),
            JOB_IDENTIFIER = new Property<>("job_id", NULL),
            CLIENT_EMAIL = new Property<>("email", NULL),
            NUMBER_OF_HIDDEN_NEURONS = new Property<>("hidden", "-h"),
            NUMBER_OF_INPUT_NEURONS = new Property<>("input", "-i"),
            TRAINING_SET_URI = new Property<>("training", "-tr"),
            TESTING_SET_URI = new Property<>("testing", "-te"),
            NUMBER_OF_EPOCHS = new Property<>("epochs", "-e"),
            ARGS = new Property<>("args", " ");

    public static boolean isNull(Property<String, String> A) {
        return A.getB().equals(NULL);
    }
    
    public static Property<String, String> fromString(String s) {
        if (s.equals(EXECUTABLE_NAME.getA())) {
            return EXECUTABLE_NAME;
        }
        if (s.equals(JOB_NAME.getA())) {
            return JOB_NAME;
        }
        if (s.equals(JOB_IDENTIFIER.getA())) {
            return JOB_IDENTIFIER;
        }
        if (s.equals(CLIENT_EMAIL.getA())) {
            return CLIENT_EMAIL;
        }
        if (s.equals(NUMBER_OF_HIDDEN_NEURONS.getA())) {
            return NUMBER_OF_HIDDEN_NEURONS;
        }
        if (s.equals(NUMBER_OF_INPUT_NEURONS.getA())) {
            return NUMBER_OF_INPUT_NEURONS;
        }
        if (s.equals(TRAINING_SET_URI.getA())) {
            return TRAINING_SET_URI;
        }
        if (s.equals(TESTING_SET_URI.getA())) {
            return TESTING_SET_URI;
        }
        if (s.equals(NUMBER_OF_EPOCHS.getA())) {
            return NUMBER_OF_EPOCHS;
        }
        if (s.equals(ARGS.getA())) {
            return ARGS;
        }
        System.out.println("CANNOT BE NULL");
        return null;
    }

}
