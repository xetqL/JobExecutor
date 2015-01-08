/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.compute;

/**
 *
 * @author anthob
 */
class UnspecifiedPropertyException extends Exception {

    public UnspecifiedPropertyException() {
        super("Must specifiy this property in the request");
    }
    
}
