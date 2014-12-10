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
public class CredentialProvider{
    private static final String 
            encryptedPassowrd = "4UMQhLBiJUcdb60kv2eqdA==",
            encryptedUser     = "l5ANMHcdBgxd5iwkLhutV8L+Cxg+Y/qhJO+1fhsVzFg=";
    public static Credential provideCredential(String key){
        AES.setKey(key);
        return new Credential(AES.decrypt(encryptedUser), AES.decrypt(encryptedPassowrd));
    }
}
