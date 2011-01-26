/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.activedd.google.extensions.fbchat.controller;

import java.security.ProtectionDomain;

/**
 *
 * @author prog
 */
public class test {

    public static ProtectionDomain domain;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        domain=new test().getClass().getProtectionDomain();
        System.out.println("Location: "+domain.getCodeSource().getLocation().getPath());
    }

}
