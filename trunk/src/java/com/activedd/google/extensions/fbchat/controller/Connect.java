/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import com.google.code.facebookapi.FacebookException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jivesoftware.smack.XMPPException;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author prog
 */
public class Connect extends MultiActionController {

    HttpSession session;

    public void connect(HttpServletRequest request, HttpServletResponse response) {
        try {
            //TO DO: go online on facebook.
            //get the seesion key from url as parameter
            chatClient=new ChatClient();
            session = request.getSession();
            String sessionkey = (String) session.getAttribute("sessionkey");
            chatClient.login(sessionkey);
            session.setAttribute("client", chatClient);
        } catch (Exception ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect(HttpServletRequest request, HttpServletResponse response) {
        //TO DO: go offline.
        chatClient.disconnect();
    }
    private ChatClient chatClient;

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
}
