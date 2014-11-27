/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachelor.boulmier.workmaster.queuing;

/**
 *
 * @author anthob
 */
public class RequestBuilder {
    private final Request r;

    private RequestBuilder(Request r) {
        this.r = r;
    }
       
    public static RequestBuilder builder(){
        return new RequestBuilder(new Request());
    }
    
    public RequestBuilder withExecutableName(String name){
        r.setExecutableName(name);
        return this;
    }
    
    public RequestBuilder withNumberOfHiddenNeurons(int n){
        r.setNumberOfHiddenNeurons(String.valueOf(n));
        return this;
    }
    
    public RequestBuilder withTrainingSetURI(String uri){
        r.setTrainingSetURI(uri);
        return this;
    }
    
    public RequestBuilder withTestingSetURI(String uri){
        r.setTestingSetURI(uri);
        return this;
    }
    
    public RequestBuilder withNumberOfInputNeurons(int n){
        r.setNumberOfInputNeurons(String.valueOf(n));
        return this;
    }
    
    public RequestBuilder withNumberOfEpochs(int n){
        r.setNumberOfEpochs(String.valueOf(n));
        return this;
    }
    
    public Request create(){
        return r;
    }
    
}
