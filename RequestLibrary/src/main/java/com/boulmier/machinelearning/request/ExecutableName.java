/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boulmier.machinelearning.request;

import java.io.Serializable;

/**
 *
 * @author anthob
 */
public enum ExecutableName implements Serializable {

    DIMLP("DIMLP", "./DimlpBT"),
    ELM("ELM", "octave elm.m"),
    ELM_DIMLP("ELMDIMLP", "octave hybrid.m"),
    SLEEP("SLEEP", "sleep"),
    TOUCH("TOUCH", "touch"),
    CAT("CAT", "cat");

    private final String name,
            execName;

    private ExecutableName(String name, String execName) {

        this.name = name;

        this.execName = execName;

    }

    public static ExecutableName buildFromName(String name) {
        if (name.equals(DIMLP.name)) {
            return DIMLP;
        }
        if (name.equals(ELM.name)) {
            return ELM;
        }
        if (name.equals(ELM_DIMLP.name)) {
            return ELM_DIMLP;
        }
        if (name.equals(SLEEP.name)) {
            return SLEEP;
        }
        if (name.equals(CAT.name)) {
            return CAT;
        }
        if (name.equals(TOUCH.name)) {
            return TOUCH;
        }
        return null;
    }

    @Override
    public String toString() {
        return execName;
    }

    public String getName() {
        return name;
    }

    public String getExecutable() {
        return execName;
    }

}
