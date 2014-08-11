/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shelloid.anotherpackage;

import java.io.Serializable;

/**
 *
 * @author jayaraj
 */
abstract public class AnotherPackageClass implements Runnable, Serializable {
    protected int something;
    
    public void doSomething(Runnable run){
        run.run();
    }
}
