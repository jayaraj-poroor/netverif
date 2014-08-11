/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.javaflow.Continuation;

/**
 *
 * @author jayaraj
 */
public class NetVerifEngine {

    private static final NetVerifEngine instance = new NetVerifEngine();

    private static InetAddress anyAddr;
    public static void init() throws Exception{
        org.shelloid.netverif.Socket.setSocketImplFactory(new NetVerifSocketImplFactory());
        org.shelloid.netverif.ServerSocket.setSocketFactory(new NetVerifSocketImplFactory());
        anyAddr = InetAddress.getByName("0.0.0.0");
    }
    
    public static InetAddress anyAddress(){
        return anyAddr;
    }
   
    public static NetVerifEngine getInstance(){
        return instance;
    }

    AtomicInteger nextPort = new AtomicInteger(10000);
    private final ThreadLocal<String> currentHost =
         new ThreadLocal<String>() {
             @Override protected String initialValue() {
                 return "localhost";
         }
     };

    public class Agent{
        String host;
        Runnable routine;
        
        public Agent(String host, Runnable routine){
            this.host = host;
            this.routine = routine;
        }
        
        public String getHost(){
            return host;
        }
        
        public Runnable getRoutine(){
            return routine;
        }
    }
    
    HashSet<Agent> agents;
    LinkedList<Event> events;
       
    private NetVerifEngine(){
        this.agents = new HashSet<Agent>();
        this.events = new LinkedList<Event>();
    }
    
    
    public void schedule(String host, Runnable routine){
        synchronized(agents){
            agents.add(new Agent(host, routine));
        }
    }
    
    public int getAvailablePort(String host){
        return nextPort.getAndIncrement();
    }
    
    public void start(){
        Iterator<Agent> it = agents.iterator();
        while(it.hasNext()){
            Agent agent = it.next();
            Continuation c = Continuation.startSuspendedWith(agent.getRoutine());
            Event e = new Event(agent, c, new EventMeta(EventMeta.REASON.AGENT_START, null, null));
            events.addLast(e);
        }
        while(!events.isEmpty()){
            Event e = events.removeFirst(); 
            this.setCurrentHost(e.getAgent().getHost());
            Continuation c = Continuation.continueWith(e.getContinuation());
            if(c != null){
                EventMeta m = (EventMeta)c.value();
                processEvent(new Event(e.getAgent(), c, m));
            }
        }
    }
    
    HashMap<InetSocketAddress, Event> pendingConnects = new HashMap<InetSocketAddress, Event>();
    HashMap<InetSocketAddress, Event> pendingAccepts = new HashMap<InetSocketAddress, Event>();
    
    void processEvent(Event e){
        System.out.println("Processing Event from: " + e.getAgent().getHost() + " Type: " + e.getMeta().getReason());
        EventMeta m = e.getMeta();
        switch(m.getReason()){
            case SOCKET_CONNECT:
                NetVerifSocketImpl sock = (NetVerifSocketImpl) m.getObj();
                InetSocketAddress remoteAddr = (InetSocketAddress) m.getParam();
                Event accept = pendingAccepts.get(remoteAddr);
                if(accept != null){
                    establishConnection(accept, e);
                }else{
                    pendingConnects.put(remoteAddr, e);
                }
                break;
            case SOCKET_ACCEPT:
                sock = (NetVerifSocketImpl) m.getObj();
                InetSocketAddress localAddr = sock.getLocalAddress();
                Event connect = pendingConnects.get(localAddr);
                if(connect != null){
                    establishConnection(e, connect);
                }else{
                    pendingAccepts.put(localAddr, e);
                }
                break;
            default:
                break;
        }
        
    }
    
    public void establishConnection(Event accept, Event connect){
        EventMeta mAccept = accept.getMeta();
        EventMeta mConnect = connect.getMeta();
        NetVerifSocketImpl sAccept = (NetVerifSocketImpl) mAccept.getObj();
        NetVerifSocketImpl sConnect = (NetVerifSocketImpl) mConnect.getObj();
        
        NetVerifSocketImpl acceptRemoteSock = (NetVerifSocketImpl)mAccept.getParam(); 
        acceptRemoteSock.setRemoteAddr(sConnect.getLocalAddress());
        events.addLast(accept);
        events.addLast(connect);
    }

    public void setCurrentHost(String host) {
        currentHost.set(host);
    }
    
    public String getCurrentHost(){
        return currentHost.get();
    }
}
