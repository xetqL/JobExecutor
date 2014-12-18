package com.boulmier.machinelearning.request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Request implements Iterable<Map.Entry<Property<String, String>, String>> {

    private final HashMap<Property<String, String>, String> 
            contentValues = new HashMap<>(),
            setupValues = new HashMap<>();

    public void setNumberOfEpochs(String numberOfEpochs) {
        contentValues.put(RequestProperty.NUMBER_OF_EPOCHS, numberOfEpochs);
    }

    public void setExecutableName(ExecutableName executableName) {
        setupValues.put(RequestProperty.EXECUTABLE_NAME, executableName.toString());
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

    public void setJobName(String jobName) {
        setupValues.put(RequestProperty.JOB_NAME, jobName);
    }

    public void setJobIdentifier(String jobIdentifier) {
        setupValues.put(RequestProperty.JOB_IDENTIFIER, jobIdentifier);
    }

    public ExecutableName getExecutableName() {
        return ExecutableName.buildFromName(setupValues.get(RequestProperty.EXECUTABLE_NAME));
    }

    public String getExcutableNameAsString() {
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

    public String getJobName() {
        return setupValues.get(RequestProperty.JOB_NAME);
    }

    public String getJobIdentifier() {
        return setupValues.get(RequestProperty.JOB_IDENTIFIER);
    }

    public String getProperty(Property p) {
        if (RequestProperty.isNull(p)) {
            assert (setupValues.containsKey(p));
            return setupValues.get(p);
        } else {
            assert (contentValues.containsKey(p));
            return contentValues.get(p);
        }
    }

    public void setProperty(Property property, String value) {
        if (RequestProperty.isNull(property)) {
            setupValues.put(property, value);
        } else {
            contentValues.put(property, value);
        }
    }

    @Override
    public Iterator<Map.Entry<Property<String, String>, String>> iterator() {
        return contentValues.entrySet().iterator();
    }

    public Map<Property, String> extractSetup() {
        return (Map<Property, String>) setupValues.clone();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{\n").append("\t\"setup\":").append(setupValues).append(",\n")
                .append("\t\"content\":").append(contentValues).append("\n}");
        return str.toString();
    }
    /**
     * Used to build a request object from a json string
     * @param s the json string
     * @return The request object corresponding to the json string (s) 
     */
    public static Request fromString(String s){
        JSONParser jp = new JSONParser();
        ContainerFactory cf = new ContainerFactory() {

            @Override
            public Map<String,Map<String,String>> createObjectContainer() {
                return new HashMap<>();
            }

            @Override
            public List<Map<String,String>> creatArrayContainer() {
                return new LinkedList<>();
            }
        };
        Request r = new Request();
        
        try{
            Map<String,Map<String,String>> json = (Map<String,Map<String,String>>) jp.parse(s,cf);
            Iterator<Map.Entry<String,Map<String,String>>> it = json.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,Map<String,String>> entry = it.next();
                Iterator<Map.Entry<String,String>> subIt = entry.getValue().entrySet().iterator();
                while(subIt.hasNext()){
                    Map.Entry<String,String> subEntry = subIt.next();
                    r.setProperty(Property.fromString(subEntry.getKey()), subEntry.getValue());
                }
            }   
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        
        return r;
    }
}
