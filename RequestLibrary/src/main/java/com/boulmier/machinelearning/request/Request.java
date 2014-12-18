package com.boulmier.machinelearning.request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Request implements Iterable<Map.Entry<RequestProperty,String>>{

    private final HashMap<RequestProperty, String> 
            contentValues = new HashMap<>(),
            setupValues    = new HashMap<>();
    public void setNumberOfEpochs(String numberOfEpochs) {
        contentValues.put(RequestProperty.NUMBER_OF_EPOCHS, numberOfEpochs);
    }

    public void setExecutableName(String executableName) {
        setupValues.put(RequestProperty.EXECUTABLE_NAME, executableName);
    }

    public void setNumberOfHiddenNeurons(String numberOfHiddenNeurons) {
        contentValues.put(RequestProperty.NUMBER_OF_HIDDEN_NEURONS, numberOfHiddenNeurons);
    }

    public void setNumberOfInputNeurons(String numberOfInputNeurons) {
        contentValues.put(RequestProperty.NUMBER_OF_INPUT_NEURONS, numberOfInputNeurons);
    }

    public void setTestingSetURI(String testingSetURI) {
        contentValues.put(RequestProperty.TESTING_SET_URI, testingSetURI);
    }

    public void setTrainingSetURI(String trainingSetURI) {
        contentValues.put(RequestProperty.TRAINING_SET_URI, trainingSetURI);
    }

    public void setJobName(String jobName){
        setupValues.put(RequestProperty.JOB_NAME, jobName);
    }
    
    public void setJobIdentifier(String jobIdentifier){
        setupValues.put(RequestProperty.JOB_IDENTIFIER, jobIdentifier);
    }
    
    public String getExecutableName() {
        return setupValues.get(RequestProperty.EXECUTABLE_NAME);
    }

    public String getNumberOfHiddenNeurons() {
        return contentValues.get(RequestProperty.NUMBER_OF_HIDDEN_NEURONS);
    }

    public String getNumberOfInputNeurons() {
        return contentValues.get(RequestProperty.NUMBER_OF_INPUT_NEURONS);
    }

    public String getTestingSetURI() {
        return contentValues.get(RequestProperty.TESTING_SET_URI);
    }

    public String getTrainingSetURI() {
        return contentValues.get(RequestProperty.TRAINING_SET_URI);
    }

    public String getNumberOfEpochs() {
        return contentValues.get(RequestProperty.NUMBER_OF_EPOCHS);
    }

    public String getJobName(){
        return setupValues.get(RequestProperty.JOB_NAME);
    }
    
    public String getJobIdentifier(){
        return setupValues.get(RequestProperty.JOB_IDENTIFIER);
    }
    
    public String getProperty(RequestProperty p){
         
        if(p.equalsOpt(RequestProperty.NULL)){
            assert(setupValues.containsKey(p));
            return setupValues.get(p);
        }else{
            assert(contentValues.containsKey(p));
            return contentValues.get(p);
        }
        
    }
    
    @Override
    public Iterator<Map.Entry<RequestProperty, String>> iterator() {
        return contentValues.entrySet().iterator();
    }
    
    public Map<RequestProperty, String> extractSetup(){
        return (Map<RequestProperty, String>) setupValues.clone();
    }
}
