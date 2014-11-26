package com.bachelor.boulmier.workmaster.queuing;

import java.util.HashMap;

/**
 *
 * @author anthob
 */
public class Request extends HashMap<String,String>{

    public Request() {
    }
    
    public void setExecutableName(String name){
        put("executablename",name);
    }
    
    public void setNumberOfHiddenNeurons(int n){
        put("hidden",String.valueOf(n));
    }
    
    public void setNumberOfEpoch(int n){
        put("epoch",String.valueOf(n));
    }
    
    public void setNumberOfInputNeurons(int n){
        put("input", String.valueOf(n));
    }
        
    public void setTrainingSetURI(String uri){
        put("trainingset", uri);
    }
    
    public void setTestingSetURI(String uri){
        put("testingset", uri);
    }
}
