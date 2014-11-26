package com.boulmier.machinelearning.jobexecutor.events;

import com.boulmier.machinelearning.jobexecutor.job.Job;
import java.util.LinkedList;

/**
 * use javaSysMon to set the maximum of parallel job
 * @author antho
 */
public class EventsQueue {
    
    private final LinkedList<Job> queues;
    
    public EventsQueue() {
        queues = new LinkedList<>();
    }
    
    public Job next(){
        return queues.pollFirst();
    }
    
    public void addToQueue(Job job){
        queues.push(job);
    }
    
}
