/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif.test.rewrite;

/**
 *
 * @author jayaraj
 */

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.commons.javaflow.Continuation;
import org.shelloid.anotherpackage.AnotherPackageClass;


/**
 * Test for making sure that rstack works correctly. For this test we need to
 * have a stack frame that goes through multiple objects of different types.
 */
@SuppressWarnings("unused")
public final class BlackRed extends AnotherPackageClass {

	private static final long serialVersionUID = 1L;


	public void run() {
                something = 1;
		// new Black( new Red( new Black( new Suspend()))).run();
		//NoInstrument n = new NoInstrument();
                NoInstrument.exec(this);
	}
        
        public void start(){
            new Black( new Suspend()).run();
        }


        class Temp {
            public void m(Object x){}
        }
	class Black extends AnotherPackageClass {
		final Runnable r;

		public Black( Runnable r) {
			this.r = r;
		}

		public void run() {
                        Temp temp = new Temp();
                        temp.m(this);
                        String s = "foo"; // have some random variable
                        doSomething(r);
			//r.run();
		}
                
                public void doSomething(Runnable r){
                    r.run();
                }
	}


	class Red implements Runnable {
		final Suspend r;

		public Red( Suspend r) {
			this.r = r;
		}

		public void run() {
			int i = 5; // have some random variable
			r.run();
		}
	}


	class Suspend implements Runnable{
            int x;
		public void run() {
			Continuation.suspend(this);
                        System.out.println("Resumed");
                        x = 3;
		}
	}

}


