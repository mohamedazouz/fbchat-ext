/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import com.google.code.facebookapi.FacebookException;
import java.io.IOException;
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
    private String apiKey;  //Application Key
    private String apiSecret;  //Application Secert key
    private String apiId;
    private String resource ;
    private String domain ;
    private int port = 5222;

    /**
     * Connect Page is to login to facebook chat via user session key.
     * 
     * you should send me a session key in url as parameter named "sessionkey"
     * 
     * @param request
     * @param response
     */
    public void connect(HttpServletRequest request, HttpServletResponse response) throws XMPPException, InterruptedException, FacebookException, IOException {
        //TO DO: go online on facebook.
        //get the seesion key from url as parameter
        chatClient = new ChatClient();
        session = request.getSession();
        String sessionkey = request.getParameter("sessionkey");
        //String sessionkey = (String) session.getAttribute("sessionkey");
        chatClient.login(sessionkey,apiKey,apiSecret,domain,resource,port);
        session.setAttribute("client", chatClient);
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

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public void setApiID(String apiId) {
        this.apiId = apiId;
    }
    private ChatClient chatClient;

    /**
     * @param resource the resource to set
     */
    public void setResource(String resource) {
        this.resource = resource;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
}
