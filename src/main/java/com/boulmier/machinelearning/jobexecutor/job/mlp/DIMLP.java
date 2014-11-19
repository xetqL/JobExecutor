/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boulmier.machinelearning.jobexecutor.job.mlp;
import com.boulmier.machinelearning.jobexecutor.job.Job;
import java.util.HashMap;
import org.apache.commons.exec.CommandLine;

/**
 *
 * @author antho
 */
public class DIMLP extends Job{

    public DIMLP(HashMap<String,String> params, String id) {
        this.cl     = generateCommandLine(params);
        this.jobid  = id;
    }
    
    @Override
    protected final CommandLine generateCommandLine(HashMap<String,String> params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
