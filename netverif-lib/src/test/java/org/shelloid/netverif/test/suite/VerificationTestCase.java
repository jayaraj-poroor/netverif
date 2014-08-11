/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.netverif.test.suite;

/**
 *
 * @author jayaraj
 */
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;

import org.apache.commons.javaflow.Continuation;
import org.shelloid.netverif.test.rewrite.BlackRed;

public final class VerificationTestCase extends TestCase {

    private boolean fromSuite() {
        final String cl = this.getClass().getClassLoader().getClass().getName();
        ClassLoader loader = this.getClass().getClassLoader();
        int i =0;
        while(loader != null && i < 10){
            System.out.println("Loader: " + loader.getClass().getName());
            loader = loader.getParent();
        }
        return cl.contains("ClassTransformerClassLoader");
    }
    
    public void testBlackRed2() {    
        System.out.println("testBlackRed2");
        assertTrue(fromSuite());        
        final Runnable r = new BlackRed();
        final Continuation c1 = Continuation.startSuspendedWith(r);
        assertTrue(c1 != null);
        final Continuation c2 = Continuation.continueWith(c1);
        assertTrue(c2 != null);       
        final Continuation c3 = Continuation.continueWith(c2);
        assertTrue(c3 == null);              
    }
}

