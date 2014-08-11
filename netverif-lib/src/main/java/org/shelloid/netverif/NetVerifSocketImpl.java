/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketOptions;
import java.util.HashMap;
import org.apache.commons.javaflow.Continuation;

/**
 *
 * @author jayaraj
 */
public class NetVerifSocketImpl extends SocketImpl{

    boolean isStream;
    InetAddress localAddr;
    
    public NetVerifSocketImpl(){
        super();
        isStream = true;
        localAddr =null;
    }
    
    public void setRemoteAddr(InetSocketAddress addr){
        this.address = addr.getAddress();
        this.port = addr.getPort();
    }
    
    public InetSocketAddress getLocalAddress(){
        return new InetSocketAddress(this.localAddr, this.localport);
    }
    
    @Override
    public void create(boolean stream) throws IOException {
        isStream = stream;
    }

    @Override
    public void connect(String host, int port) throws IOException {
        SocketAddress saddr = new InetSocketAddress(host, port);
        connect(saddr, -1);        
    }

    @Override
    public void connect(InetAddress address, int port) throws IOException {
        SocketAddress saddr = new InetSocketAddress(address, port);
        connect(saddr,-1);
    }

    @Override
    public void connect(SocketAddress saddr, int timeout) throws IOException {
        InetSocketAddress iaddr = (InetSocketAddress)saddr;        
        System.out.println("connect: " + iaddr);        
        if(localAddr == null){
            localAddr = InetAddress.getByName(NetVerifEngine.getInstance().getCurrentHost());
        }
        this.localport = NetVerifEngine.getInstance().getAvailablePort(localAddr.getHostAddress());
        Continuation.suspend(new EventMeta(EventMeta.REASON.SOCKET_CONNECT, this, iaddr));
        this.address = iaddr.getAddress();
        this.port = iaddr.getPort();
    }
    
    @Override
    public void bind(InetAddress host, int port) throws IOException {
        InetAddress currHost = InetAddress.getByName(NetVerifEngine.getInstance().getCurrentHost());
        this.setOption(SocketOptions.SO_BINDADDR, currHost);
        this.localport = port;
        System.out.println("bind: " + currHost + " port: " + port);        
    }

    @Override
    public void listen(int backlog) throws IOException {
        //TODO
    }

    @Override
    public void accept(SocketImpl s) throws IOException {
        Continuation.suspend(new EventMeta(EventMeta.REASON.SOCKET_ACCEPT, this, s));
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int available() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendUrgentData(int data) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setOption(int optID, Object value) throws SocketException {
        if(optID == SocketOptions.SO_BINDADDR){
            localAddr = (InetAddress)value;
        }
    }

    public Object getOption(int optID) throws SocketException {
        if(optID == SocketOptions.SO_BINDADDR){
            return localAddr;
        }else
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Returns the value of this socket's <code>fd</code> field.
     *
     * @return  the value of this socket's <code>fd</code> field.
     * @see     java.net.SocketImpl#fd
     */
    public FileDescriptor getFileDescriptor() {
        return super.getFileDescriptor();
    }

    /**
     * Returns the value of this socket's <code>address</code> field.
     *
     * @return  the value of this socket's <code>address</code> field.
     * @see     java.net.SocketImpl#address
     */
    public InetAddress getInetAddress() {
        return super.getInetAddress();
    }

    /**
     * Returns the value of this socket's <code>port</code> field.
     *
     * @return  the value of this socket's <code>port</code> field.
     * @see     java.net.SocketImpl#port
     */
    public int getPort() {
        return super.getPort();
    }
    
    public int getLocalPort(){
        return super.getLocalPort();
    }
    
    public boolean supportsUrgentData(){
        return super.supportsUrgentData();
    }
    
    public void shutdownInput(){
        
    }

    public void shutdownOutput(){
        
    }
    
    public void reset() throws IOException{
        address = null;
        port = 0;
        localport = 0;    
    }
    
    public void setFields(InetAddress addr, FileDescriptor fd){//since fields are not accessible from ServerSocket
        this.address = addr;
        this.fd  = fd;
    }

}
