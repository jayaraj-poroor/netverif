/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif;

import org.apache.commons.javaflow.Continuation;

/**
 *
 * @author jayaraj
 */
public class TestRunnable2 implements Runnable{
    public TestRunnable2(){}
    public void run()
    {
        TestRunnable test = new TestRunnable();
        Continuation c = Continuation.startWith(test);
        System.out.println("returned a continuation");        
        c = Continuation.continueWith(c);
        System.out.println("returned a continuation: " + c);        
        
    }
}
