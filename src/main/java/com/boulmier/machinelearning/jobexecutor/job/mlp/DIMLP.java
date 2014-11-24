package com.boulmier.machinelearning.jobexecutor.job.mlp;
import com.boulmier.machinelearning.jobexecutor.job.Job;
import java.util.HashMap;

/**
 *
 * @author antho
 */
public class DIMLP extends Job{
    public DIMLP(HashMap<String,String> params, String id) {
        this.cl     = generateCommandLine(params);
        this.jobid  = id;
    }
}
