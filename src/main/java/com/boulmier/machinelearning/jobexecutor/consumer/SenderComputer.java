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
public class SenderComputer extends Computer{
    
    public SenderComputer(String data) {
        super(data);
    }

    public SenderComputer(Computer subComputer) {
        super(subComputer);
    }
    
    @Override
    public void compute() {
        super.compute();
    }
    
}
