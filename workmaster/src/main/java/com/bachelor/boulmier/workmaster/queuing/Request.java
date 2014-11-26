/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bachelor.boulmier.workmaster.queuing;

import java.util.HashMap;

/**
 *
 * @author anthob
 */
public class Request extends HashMap<String,String>{

    public Request(String executable,String name) {
        put("executable", executable);
        put("name",name);
    }
    
}
