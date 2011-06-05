/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

/**
 *
 * @author ibrahim
 */
import com.activedd.google.extensions.fbchat.chat.ChatClient;
import com.activedd.google.extensions.fbchat.chat.ServerConfiguration;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class NewConnect extends MultiActionController {

    HttpSession session;
    private String apiKey;  //Application Key
    private String apiSecret;  //Application Secert key
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
    public void newconnect(HttpServletRequest request, HttpServletResponse response) {
        //TO DO: go online on facebook.
        //get the seesion key from url as parameter
        try {
            chatClient = new ChatClient(configuration.getConfiguration());
            //create a new session if there is no session associated with request.
            session = request.getSession(true);
            JSONObject jSONObject = null;
            int status = 0;
            String message = "";
            if (request.getParameter("sessionkey") != null) {
                String sessionkey = request.getParameter("sessionkey");
                try {
                    sessionkey = sessionkey.substring(sessionkey.indexOf("|") + 1, sessionkey.lastIndexOf("|"));
                    chatClient.xmppConnectAndLogin(sessionkey, apiKey, getApiSecret(), domain, resource, port);
                    session.setAttribute("client", chatClient);
                    status = 200;
                    message = "success";
                } catch (Exception e) {
                    status = 417;
                    message = "Expectation Failed";
                }
            } else {
                status = 400;
                message = "Bad Request";
            }
            jSONObject = new JSONObject("{status:" + status + ",message:" + message + "}");
            jSONObject.write(response.getWriter());
            response.getWriter().close();
        } catch (Exception e) {
        }
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
            JSONObject jSONObject = new JSONObject("{error:no sessionkey found}");
            jSONObject.write(response.getWriter());
            response.getWriter().close();
        }
    }

    /**
     *
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
