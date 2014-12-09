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
public abstract class Computer {
    protected Computer sub = null;
    protected final String data;
    
    public Computer(Computer subComputer) {
        sub = subComputer;
        data = sub.getData();
    }

    public Computer(String data) {
        this.data = data;
    }
    
    public void compute(){
       if(sub != null) sub.compute();
    }
    
    private String getData(){
        return data;
    }
}
