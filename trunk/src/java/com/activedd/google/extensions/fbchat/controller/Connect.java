/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.activedd.google.extensions.fbchat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author prog
 */
public class Connect extends MultiActionController{


    public void connect(HttpServletRequest request,HttpServletResponse response){
        //TO DO: go online on facebook.
    }

    public void disconnect(HttpServletRequest request,HttpServletResponse response){
        //TO DO: go offline.
    }
}
