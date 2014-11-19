/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boulmier.machinelearning.jobexecutor.events;

import com.boulmier.machinelearning.jobexecutor.JobExecutor;
import com.boulmier.machinelearning.jobexecutor.job.Job;
import java.util.LinkedList;
import java.util.List;

/**
 * use javaSysMon to set the maximum of parrallel job
 * @author antho
 */
public class EventsQueue {
    
    private final int maxPar = JobExecutor.sysMon.numCpus();
    private int current      = 0;
    private final LinkedList<Job>[] queues;
    
    public EventsQueue() {
        queues = new LinkedList[maxPar];
    }
    
    public Job next(){
        Job j = queues[current].pollFirst();
        current = (current+1) % maxPar;
        return j;
    }
    
    public void addToQueue(Job job){
        int min = current;
        
        for(int i = 0;i<queues.length;i++) {
            List l = queues[i];
            min = l.size() < queues[min].size() ? i:min;
        }
        queues[min].push(job);
    }
    
}
