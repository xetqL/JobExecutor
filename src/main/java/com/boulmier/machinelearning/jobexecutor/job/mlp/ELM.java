/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boulmier.machinelearning.jobexecutor.job.mlp;

import com.boulmier.machinelearning.jobexecutor.job.Job;
import java.util.HashMap;

/**
 *
 * @author antho
 */
public class ELM extends Job{

    public ELM(HashMap<String,String> params, String id) {
        this.cl     = generateCommandLine(params);
        this.jobid  = id;
    } 
}
