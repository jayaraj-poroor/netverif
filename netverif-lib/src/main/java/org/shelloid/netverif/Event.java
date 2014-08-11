/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif;

import org.apache.commons.javaflow.Continuation;
import org.shelloid.netverif.NetVerifEngine.Agent;

/**
 *
 * @author jayaraj
 */
public class Event {
    Continuation c;
    EventMeta meta;
    Agent agent;
    
    public Event(Agent agent, Continuation c, EventMeta meta){
        this.agent = agent;
        this.meta = meta;
        this.c = c;
    }
    
    public Agent getAgent(){
        return agent;
    }
    
    public Continuation getContinuation(){
        return this.c;
    }
        
    public EventMeta getMeta(){
        return meta;
    }
}
