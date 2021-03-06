/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.compute;

import com.boulmier.machinelearning.jobexecutor.mail.EmailService;
import com.boulmier.machinelearning.request.RequestProperty;
import java.io.File;

/**
 *
 * @author antho
 */
public class SenderComputer extends Computer{
    
    public SenderComputer(String data, ComputeProperties properties) {
        super(data, properties);
    }

    public SenderComputer(Computer subComputer) {
        super(subComputer);
    }
    
    @Override
    public void compute() {
        super.compute();
        EmailService.sendWith(
                new File(properties.getProperty(RequestProperty.JOB_IDENTIFIER)), 
                properties.getProperty(RequestProperty.CLIENT_EMAIL)
        );
    }
    
}
