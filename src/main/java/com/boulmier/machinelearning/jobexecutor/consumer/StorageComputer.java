/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.consumer;

/**
 *
 * @author antho
 */
public class StorageComputer extends Computer{

    public StorageComputer(Computer subComputer) {
        super(subComputer);
    }

    public StorageComputer(String data) {
        super(data);
    }

    @Override
    public void compute() {
        super.compute(); 
        //store
    }
    
}
