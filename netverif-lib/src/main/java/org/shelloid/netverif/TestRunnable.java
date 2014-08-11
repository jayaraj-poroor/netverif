/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif;

import java.io.Serializable;
import org.apache.commons.javaflow.Continuation;

/**
 *
 * @author jayaraj
 */
public class TestRunnable implements Runnable, Serializable {
  private static final long serialVersionUID = 1L;
  
  public TestRunnable(){
        ClassLoader loader = this.getClass().getClassLoader();
        int i =0;
        while(loader != null && i < 10){
            System.out.println("Loader: " + loader.getClass().getName());
            loader = loader.getParent();
        }
      
  }
  public void run() {
    System.out.println("started!");
    for( int i=0; i<1; i++ )
      echo(i);
  }
  private void echo(int x) {
    System.out.println(x);
    Continuation.suspend();
  }
}
