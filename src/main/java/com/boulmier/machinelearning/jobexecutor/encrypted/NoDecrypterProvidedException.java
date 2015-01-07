/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.encrypted;

/**
 *
 * @author anthob
 */
class NoDecrypterProvidedException extends Exception {

    public NoDecrypterProvidedException() {
        super("Must specify an AES decrypter to provide this credential");
    }
    
}
