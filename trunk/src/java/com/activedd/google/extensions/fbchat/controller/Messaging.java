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
public class Messaging extends MultiActionController{

    public void send(HttpServletRequest request,HttpServletResponse response){
        System.out.println("sending");
        //TO DO: send a message,
    }

    public void onlinefriends(HttpServletRequest request,HttpServletResponse response){
        //TO DO: get list of online friends.
    }
}