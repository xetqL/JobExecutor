/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.jobexecutor.encrypted;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;

/**
 *
 * @author anthob
 */
public class EncryptedCredential extends Credential {

    private AES aes = null;

    public EncryptedCredential(String user, String password) {
        super(user, password);
    }

    public EncryptedCredential withDecrypter(AES aes) {
        this.aes = aes;
        return this;
    }
    
    private String getDecryptedCredential(final String credential) throws NoDecrypterProvidedException{
         if(aes == null) throw new NoDecrypterProvidedException();
         return aes.decrypt(credential);
    }
    
    @Override
    public String getUser() {
        try {
            return getDecryptedCredential(super.getUser());
        } catch (NoDecrypterProvidedException ex) {
            JobExecutor.logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public String getPassword() {
        try {
            return getDecryptedCredential(super.getPassword());
        } catch (NoDecrypterProvidedException ex) {
            JobExecutor.logger.error(ex.getMessage());
            return null;
        }
    }

    public Credential returnAsCredential() {
        assert (aes != null);
        return new Credential(this.getUser(), this.getPassword());
    }

    @Override
    public String toString() {
        return aes != null ? getUser() + ":" + getPassword() : super.toString();
    }

}
