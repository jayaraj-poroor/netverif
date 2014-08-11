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
public class TestRunnable3 implements Runnable{
    
    public TestRunnable3(){}
    public void run(){
        TestRunnable2 run = new TestRunnable2();
        run.run();
    }
    
}
