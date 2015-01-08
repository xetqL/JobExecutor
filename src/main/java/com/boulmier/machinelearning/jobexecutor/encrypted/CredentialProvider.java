/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.encrypted;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author anthob
 */
public class CredentialProvider{
    public static enum CredentialSource{
        EMAIL, MONGODB, SWIFT
    }
    private static final Map<CredentialSource, EncryptedCredential> credentialDB = new HashMap<>();
    
    static {
        credentialDB.put( CredentialSource.EMAIL, new EncryptedCredential( "pP/l2Eo0ltz+THSCQ7ZlH5Gsrqc3rVcFdd70gTeXi00=", "4UMQhLBiJUcdb60kv2eqdA==") );
    }

    public static Credential provideCredential(String key, CredentialSource source){
        AES aes = new AES(key);
        EncryptedCredential encryptedCred = credentialDB.get(source).withDecrypter(aes);
        return encryptedCred.returnAsCredential();
    }
}
