/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author prog
 */
public class Login extends MultiActionController {

    HttpSession session;
    private FacebookJsonRestClient facebook;
    private String apiKey = "76f98c6f348e8d27ed504ae74da69cea";  //Application Key
    private String apiSecret = "c4cc0e40e6f8f17362685640a9b0adb4";  //Application Secert key

    /**
     * login page its first time login to show to the user permission of the application
     * then redirect it to the application home which is authenticate page to generate session key
     *
     * nothing need to send via url as parameter
     * 
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //String redirct = "http://www.facebook.com/login.php?api_key=172430629459688&connect_display=popup&v=1.0&next=http://www.facebook.com/login.php?api_key=76f98c6f348e8d27ed504ae74da69cea&v=1.0/&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access";
        String redirct = generateLoginURL();
        response.sendRedirect(redirct);
        //TO DO: redirect to the login screen in facebook.
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
    public ModelAndView authenticate(HttpServletRequest request, HttpServletResponse response) throws FacebookException {
        //TO DO: this url that will facebook redirects to after authenticating application from facebook.
        //      and set the user key in the httpsession.
        session = request.getSession();
        String token = request.getParameter("auth_token");
        facebook = new FacebookJsonRestClient(apiKey, apiSecret);
        String FB_SESSION_KEY = facebook.auth_getSession(token);
        session.setAttribute("sessionkey", FB_SESSION_KEY);
        return new ModelAndView("thankYou");
    }

    /**
     * getauthkey Page is to send session key value to the user.
     *
     * nothing need to send via url as parameter
     *
     * @param request
     * @param response
     */
    public void getauthkey(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        //TO DO: check if the user has authenticated from facebook by checking http session and if he does, then populate the user key/id in the respose and delete it from http session.
        response.setContentType("application/json;charset=UTF-8");

        String sessionkey = (String) session.getAttribute("sessionkey");
        session.removeAttribute("sessionkey");
        JSONObject jSONObject = new JSONObject();

        if (sessionkey != null) {
            jSONObject.put("sessionkey", sessionkey);
        }

        jSONObject.write(response.getWriter());

        response.getWriter().close();
        //response like {"sessionkey":"xxx"}
        //if there is no session key in the session response like: {}

    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    private String generateLoginURL() {
        String apiID = "172430629459688";

        String nextPage = "next=http://www.facebook.com/login.php?api_key=" + apiKey + "&v=1.0/&&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=";


        String permission = "user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access";

        String authurl = "http://www.facebook.com/login.php?api_key=" + apiID + "&connect_display=popup&v=1.0&" + nextPage + permission;

        // String redirct = "http://www.facebook.com/login.php?api_key=172430629459688&connect_display=popup&v=1.0&next=http://www.facebook.com/login.php?api_key=76f98c6f348e8d27ed504ae74da69cea&v=1.0/&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access";

        return authurl;
    }
}
