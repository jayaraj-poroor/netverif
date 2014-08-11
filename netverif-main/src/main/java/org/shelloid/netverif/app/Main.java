/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif.app;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.shelloid.netverif.NetVerifEngine;
import org.shelloid.netverif.NetVerifSocketImpl;
import org.shelloid.netverif.TestRunnable3;
/**
 *
 * @author jayaraj
 */
public class Main {
          
    public static void main(String []args) throws Exception{
        NetVerifEngine.init();
        final NetVerifEngine engine = NetVerifEngine.getInstance();
        engine.schedule("192.168.10.1", new Runnable(){
            public void run(){
                try{
                    ServerSocket sock = new org.shelloid.netverif.ServerSocket(8000);
                    System.out.println("Waiting on accept");
                    Socket s = sock.accept();
                    System.out.println("Accepted: " + s.getRemoteSocketAddress());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        engine.schedule("192.168.10.2", new Runnable(){
            public void run(){
                try{
                    Socket sock = new org.shelloid.netverif.Socket();
                    System.out.println("Going to connect");
                    sock.connect(new InetSocketAddress("192.168.10.1", 8000));
                    System.out.println("Connected: " + sock.getRemoteSocketAddress());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        engine.start();
    }
}
