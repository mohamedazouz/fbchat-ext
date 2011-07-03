/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author prog
 */
public class Login extends MultiActionController {

    HttpSession session;
    private String apiSecret;  //Application Secert key
    private String apiId;
    private String redirectUrl;
    private String permissionTokens;

    /**
     * login page its first time login to show to the user permissions of the application
     * then redirect it to the application home which is authenticate page to generate session key
     *
     * nothing need to send via url as parameter
     *
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response){


        try {
            String redirct = generateLoginURL();
            response.sendRedirect(redirct);
        } catch (IOException ex) {
        }

    }

    /**
     * authenticate Page is to authenticate user from facebook to get user session key from auth_token value
     * which facebook send via redirected url.
     *
     * nothing need to send via url as parameter
     *
     * @param request
     * @param response
     */
    public void authenticate(HttpServletRequest request, HttpServletResponse response){
        //this url that will facebook redirects to after authenticating application from facebook.
        //      and set the user key in the httpsession.
        //creates a new session if there is not session.
        session = request.getSession(true);
        StringBuilder sessionkey=new StringBuilder("");
        //get the request token from the request.
        if (request.getParameter("code") != null) {
            try {
                String token = request.getParameter("code");
                //generates the authintication token for the user.
                String link = generateGetSessionKeyUrl(token);
                URL url = new URL(link);
                Scanner s = new Scanner(url.openStream());
                while (s.hasNextLine()) {
                    sessionkey.append(s.nextLine());
                }
                sessionkey = sessionkey.delete(0, sessionkey.indexOf("=") + 1);
                session.setAttribute("sessionkey", sessionkey.toString());
                response.sendRedirect("../thankyou.htm");
            } catch (IOException ex) {
            }
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * getauthkey Page is to send session key value to the user.
     *
     * nothing need to send via url as parameter
     *
     * @param request
     * @param response
     */
    public void getauthkey(HttpServletRequest request, HttpServletResponse response) {
        try {
            //check if the user has authenticated from facebook by checking http session and if he does, then populate the user key/id in the respose and delete it from http session.
            response.setContentType("application/json;charset=UTF-8");
            //must instanciate the session again from the request.
            session = request.getSession(false);
            //if there is no session in the request, stop proceeding the function.
            if (session == null) {
                return;
            }
            String token = (String) session.getAttribute("sessionkey");
            session.removeAttribute("sessionkey");
            JSONObject jSONObject = new JSONObject();
            if (token != null) {
                jSONObject.put("sessionkey", token);
            }
            jSONObject.write(response.getWriter());
            response.getWriter().close();
        } catch (Exception ex) {
        }
    }


    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public void setApiID(String apiId) {
        this.apiId = apiId;
    }

    private String generateLoginURL() {
        String permissions = permissionTokens;
        String authurl = authurl = "https://www.facebook.com/dialog/oauth?client_id=" + apiId + "&redirect_uri=" + redirectUrl + "&scope=" + permissions;
        return authurl;
    }
    private String generateGetSessionKeyUrl(String token){
        String sessionKeyUrl="https://graph.facebook.com/oauth/access_token?client_id=" + apiId
                    + "&redirect_uri=" + redirectUrl + "&client_secret=" + apiSecret + "&code=" + token;
        return sessionKeyUrl;
    }

    /**
     * @param redirectUrl the redirectUrl to set
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * @param permissionTokens the permissionTokens to set
     */
    public void setPermissionTokens(String permissionTokens) {
        this.permissionTokens = permissionTokens;
    }
}

