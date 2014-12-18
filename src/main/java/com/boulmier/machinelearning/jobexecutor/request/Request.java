package com.boulmier.machinelearning.jobexecutor.request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Request implements Iterable<Map.Entry<RequestProperty,String>>{

    private final HashMap<RequestProperty, String> content = new HashMap<>();

    public void setNumberOfEpochs(String numberOfEpochs) {
        content.put(RequestProperty.NUMBER_OF_EPOCHS, numberOfEpochs);
    }

    public void setExecutableName(String executableName) {
        content.put(RequestProperty.EXECUTABLE_NAME, executableName);
    }

    public void setNumberOfHiddenNeurons(String numberOfHiddenNeurons) {
        content.put(RequestProperty.NUMBER_OF_HIDDEN_NEURONS, numberOfHiddenNeurons);
    }

    public void setNumberOfInputNeurons(String numberOfInputNeurons) {
        content.put(RequestProperty.NUMBER_OF_INPUT_NEURONS, numberOfInputNeurons);
    }

    public void setTestingSetURI(String testingSetURI) {
        content.put(RequestProperty.TESTING_SET_URI, testingSetURI);
    }

    public void setTrainingSetURI(String trainingSetURI) {
        content.put(RequestProperty.TRAINING_SET_URI, trainingSetURI);
    }

    public void setJobName(String jobName){
        content.put(RequestProperty.JOB_NAME, jobName);
    }
    
    public void setJobIdentifier(String jobIdentifier){
        content.put(RequestProperty.JOB_IDENTIFIER, jobIdentifier);
    }
    
    public String getExecutableName() {
        return content.get(RequestProperty.EXECUTABLE_NAME);
    }

    public String getNumberOfHiddenNeurons() {
        return content.get(RequestProperty.NUMBER_OF_HIDDEN_NEURONS);
    }

    public String getNumberOfInputNeurons() {
        return content.get(RequestProperty.NUMBER_OF_INPUT_NEURONS);
    }

    public String getTestingSetURI() {
        return content.get(RequestProperty.TESTING_SET_URI);
    }

    public String getTrainingSetURI() {
        return content.get(RequestProperty.TRAINING_SET_URI);
    }

    public String getNumberOfEpochs() {
        return content.get(RequestProperty.NUMBER_OF_EPOCHS);
    }

    public String getJobName(){
        return content.get(RequestProperty.JOB_NAME);
    }
    
    public String getJobIdentifier(){
        return content.get(RequestProperty.JOB_IDENTIFIER);
    }
    @Override
    public Iterator<Map.Entry<RequestProperty, String>> iterator() {
        return content.entrySet().iterator();
    }
}
