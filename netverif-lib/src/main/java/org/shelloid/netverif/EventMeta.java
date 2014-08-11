/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif;

/**
 *
 * @author jayaraj
 */
public class EventMeta {
    public static enum REASON {NONE, AGENT_START, SOCKET_CONNECT, SOCKET_ACCEPT};
    
    REASON reason;
    Object obj;
    Object param;
    
    public EventMeta(REASON reason, Object obj, Object param){
        this.reason = reason;
        this.obj = obj;        
        this.param = param;
    }
    
    public Object getObj(){
        return obj;
    }

    public Object getParam(){
        return param;
    }
    
    public REASON getReason(){
        return reason;
    }
    
}
