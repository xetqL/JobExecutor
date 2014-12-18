/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.request;

/**
 *
 * @author anthob
 */
public class RequestBuilder {

    private final Request r;

    private RequestBuilder(Request r) {
        this.r = r;
    }

    public static RequestBuilder builder() {
        return new RequestBuilder(new Request());
    }

    public RequestBuilder withExecutableName(ExecutableName name) {
        r.setExecutableName(name);
        return this;
    }

    public RequestBuilder withNumberOfHiddenNeurons(int n) {
        r.setNumberOfHiddenNeurons(String.valueOf(n));
        return this;
    }

    public RequestBuilder withTrainingSetURI(String uri) {
        r.setTrainingSetURI(uri);
        return this;
    }

    public RequestBuilder withTestingSetURI(String uri) {
        r.setTestingSetURI(uri);
        return this;
    }

    public RequestBuilder withNumberOfInputNeurons(int n) {
        r.setNumberOfInputNeurons(String.valueOf(n));
        return this;
    }

    public RequestBuilder withNumberOfEpochs(int n) {
        r.setNumberOfEpochs(String.valueOf(n));
        return this;
    }

    private void addArg(String arg) {
        String args = r.getProperty(RequestProperty.ARGS);
        boolean first = args == null;
        args = args == null? " ":args;
        StringBuilder actualArgList = new StringBuilder(args);
        if (first) {
            r.setProperty(RequestProperty.ARGS, arg);
        } else {
            r.setProperty(RequestProperty.ARGS, actualArgList.append(" ").append(arg).toString());
        }
    }

    public RequestBuilder with(Property property, String as) {
        if (RequestProperty.ARGS == property) {
            this.addArg(as);
        } else {
            r.setProperty(property, as);
        }
        return this;
    }

    public Request create() {
        return r;
    }

}
