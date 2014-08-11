/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif;

import java.net.SocketImpl;
import java.net.SocketImplFactory;

/**
 *
 * @author jayaraj
 */
public class NetVerifSocketImplFactory implements SocketImplFactory{

    public SocketImpl createSocketImpl() {
        return new NetVerifSocketImpl();
    }
    
}
