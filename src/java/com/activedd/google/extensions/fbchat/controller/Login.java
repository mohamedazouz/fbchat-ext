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
public class Login extends MultiActionController{

    /**
     * //write what this url does here.
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request,HttpServletResponse response){
        System.out.println("helllooooooooo");
        //TO DO: redirect to the login screen in facebook.
    }

    /**
     * //write what this url does here.
     * @param request
     * @param response
     */
    public void authenticate(HttpServletRequest request,HttpServletResponse response){
        //TO DO: this url that will facebook redirects to after authenticating application from facebook.
        //      and set the user key in the httpsession.
    }

    public void getauthkey(HttpServletRequest request,HttpServletResponse response){
        //TO DO: check if the user has authenticated from facebook by checking http session and if he does, then populate the user key/id in the respose and delete it from http session.
    }

}
