package com.boulmier.machinelearning.jobexecutor.request;

public class Request {

    private String executableName,
            numberOfHiddenNeurons,
            numberOfInputNeurons,
            trainingSetURI,
            testingSetURI,
            numberOfEpochs;

    public String getNumberOfEpochs() {
        return numberOfEpochs;
    }

    public void setNumberOfEpochs(String numberOfEpochs) {
        this.numberOfEpochs = numberOfEpochs;
    }

    public void setExecutableName(String executableName) {
        this.executableName = executableName;
    }

    public void setNumberOfHiddenNeurons(String numberOfHiddenNeurons) {
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
    }

    public void setNumberOfInputNeurons(String numberOfInputNeurons) {
        this.numberOfInputNeurons = numberOfInputNeurons;
    }

    public void setTestingSetURI(String testingSetURI) {
        this.testingSetURI = testingSetURI;
    }

    public void setTrainingSetURI(String trainingSetURI) {
        this.trainingSetURI = trainingSetURI;
    }

    public String getExecutableName() {
        return executableName;
    }

    public String getNumberOfHiddenNeurons() {
        return numberOfHiddenNeurons;
    }

    public String getNumberOfInputNeurons() {
        return numberOfInputNeurons;
    }

    public String getTestingSetURI() {
        return testingSetURI;
    }

    public String getTrainingSetURI() {
        return trainingSetURI;
    }

}
