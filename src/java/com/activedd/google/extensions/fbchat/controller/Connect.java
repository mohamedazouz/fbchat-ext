/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author prog
 */
public class Connect extends MultiActionController {

    HttpSession session;

    /**
     * Connect Page is to login to facebook chat via user session key.
     * 
     * you should send me a session key in url as parameter named "sessionkey"
     * 
     * @param request
     * @param response
     */
    public void connect(HttpServletRequest request, HttpServletResponse response) {
        try {
            //TO DO: go online on facebook.
            //get the seesion key from url as parameter
            chatClient=new ChatClient();
            session = request.getSession();
            String sessionkey = request.getParameter("sessionkey");
//            String sessionkey = (String) session.getAttribute("sessionkey");
            chatClient.login(sessionkey);
            session.setAttribute("client", chatClient);
        } catch (Exception ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * DisConnect Page is to log off from facebook chat .
     *
     * @param request
     * @param response
     */
    public void disconnect(HttpServletRequest request, HttpServletResponse response) {
        //TO DO: go offline.
        chatClient.disconnect();
        session.removeAttribute("client");
        session.removeAttribute("sessionkey");
    }
    
    private ChatClient chatClient;

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
}
