/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachelor.boulmier.workmaster.queuing;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author anthob
 */
public class QueuingExecutionTable {
    private static final HashMap<Long, Request> table = new HashMap<>();

    public static synchronized void newRequest(long tag, Request req){
        table.putIfAbsent(tag, req);
    }
    
    public static synchronized Request getRequestFromTag(long tag){
        Request r = table.get(tag);
        table.remove(tag);
        return r;
    }
    
    public static synchronized void removeEntry(long tag){
        table.remove(tag);
    }    
    
    public static synchronized Set<Long> asSetOfTag(){
        return table.keySet();
    }
}
