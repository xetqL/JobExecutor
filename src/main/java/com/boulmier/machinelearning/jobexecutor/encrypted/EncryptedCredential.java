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
public class EncryptedCredential extends Credential {

    private AES aes = null;

    public EncryptedCredential(String user, String password) {
        super(user, password);
    }

    public EncryptedCredential withDecrypter(AES aes) {
        this.aes = aes;
        return this;
    }

    @Override
    public String getUser() {
        assert (aes != null);
        return aes.decrypt(super.getUser());
    }

    @Override
    public String getPassword() {
        assert (aes != null);
        return aes.decrypt(super.getPassword()); //To change body of generated methods, choose Tools | Templates.
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
