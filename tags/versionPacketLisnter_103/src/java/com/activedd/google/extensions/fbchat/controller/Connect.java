/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import com.activedd.google.extensions.fbchat.chat.ServerConfiguration;
import com.google.code.facebookapi.FacebookException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jivesoftware.smack.XMPPException;
import org.json.JSONException;
import org.json.JSONObject;
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
    private String resource;
    private String domain;
    private int port;
    private ServerConfiguration configuration;

    /**
     * Connect Page is to xmppConnectAndLogin to facebook chat via user session key.
     * 
     * you should send me a session key in url as parameter named "sessionkey"
     * 
     * @param request
     * @param response
     */
    public void connect(HttpServletRequest request, HttpServletResponse response) throws XMPPException, InterruptedException, FacebookException, IOException, FacebookException, FacebookException, FacebookException, FacebookException, JSONException {
        //TO DO: go online on facebook.
        //get the seesion key from url as parameter
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chatClient = new ChatClient(configuration.getConfiguration());
        //create a new session if there is no session associated with request.
        session = request.getSession(true);
        String sessionkey = request.getParameter("sessionkey");
        //String sessionkey = (String) session.getAttribute("sessionkey");
        chatClient.xmppConnectAndLogin(sessionkey, apiKey, getApiSecret(), domain, resource, port);
        session.setAttribute("client", chatClient);
        JSONObject jSONObject = chatClient.getLoggedInUserDetails();
        jSONObject.write(response.getWriter());
        response.getWriter().close();

    }

    /**
     * DisConnect Page is to log off from facebook chat .
     *
     * @param request
     * @param response
     */
    public void disconnect(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        //TO DO: go offline.
        session = request.getSession();
        chatClient = (ChatClient) session.getAttribute("client");
        if (chatClient != null) {
            chatClient.disconnect();
            session.removeAttribute("client");
            session.removeAttribute("sessionkey");
        } else {
            JSONObject jSONObject = new JSONObject("{'error':'no session response'}");
            jSONObject.write(response.getWriter());
            response.getWriter().close();
        }

        //   out.close();
    }

    /**
     * idle page which set user status idle.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void idle(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        //TO DO: go away/idle.
        session = request.getSession();
        chatClient = (ChatClient) session.getAttribute("client");
        if (chatClient == null) {
            JSONObject jSONObject = new JSONObject("{'error':'no session response'}");
            jSONObject.write(response.getWriter());
            response.getWriter().close();
        } else {
            if (!chatClient.isConnected()) {
                JSONObject jSONObject = new JSONObject("{'error':'no session response'}");
                jSONObject.write(response.getWriter());
                response.getWriter().close();
            } else {
                chatClient.setIdle();
            }
        }
    }

    /**
     * online page which make user status online "Available".
     * @param request
     * @param response
     * @throws IOException
     */
    public void online(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        //TO DO: go offline.
        session = request.getSession();
        chatClient = (ChatClient) session.getAttribute("client");
        if (chatClient == null) {
            JSONObject jSONObject = new JSONObject("{'error':'no session response'}");
            jSONObject.write(response.getWriter());
            response.getWriter().close();
        } else {
            if (!chatClient.isConnected()) {
                JSONObject jSONObject = new JSONObject("{'error':'no session response'}");
                jSONObject.write(response.getWriter());
                response.getWriter().close();
            } else {
                chatClient.setOnLinee();
            }
        }
    }

    /**
     * 
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(ServerConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * @return the apiSecret
     */
    public String getApiSecret() {
        return apiSecret;
    }

    /**
     * @param apiSecret the apiSecret to set
     */
    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
}
