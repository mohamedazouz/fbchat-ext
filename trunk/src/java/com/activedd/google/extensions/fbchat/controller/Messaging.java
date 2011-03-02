/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import com.google.code.facebookapi.FacebookException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jivesoftware.smack.XMPPException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author prog
 */
public class Messaging extends MultiActionController {

    HttpSession session;
    private ChatClient chatClient;

    /**
     * send page is to send message to specific user.
     *
     * url need two parameter of sending user in key named "to" , message text in key named "msg"
     *
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    public void send(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, XMPPException, IOException {
        //send a message
        //get to ID url parameter and msg
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        session = request.getSession();
        chatClient = (ChatClient) session.getAttribute("client");
        PrintWriter out = response.getWriter();
        if (chatClient == null) {
            String output = " <div id='fb-root'></div>"
                    + " <script src='http://connect.facebook.net/en_US/all.js'></script>"
                    + " <script> "
                    + "FB.init({appId  : '172430629459688',status : true,cookie : true,xfbml  : true }); "
                    + "FB.getLoginStatus(function(response) {"
                    + "    if (response.session) {"
                    + "      FB.logout();"
                    + "    } else {"
                    + "        "
                    + "} "
                    + "  });"
                    + "</script>";
            out.println(output);
            out.close();
            //output="FB.logout(function(response) {alert(JSON.stringify(response))});";
        } else {
            String to = "-";
            String friend = request.getParameter("to");
            to += friend + "@chat.facebook.com";
            String msg = request.getParameter("msg");
            chatClient.sendMessage(msg, to);
        }
    }

    /**
     * onlinefriends page is to send all online friends with its name ,id and pic
     *
     *  nothing need to send via url as parameter
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws FacebookException
     * @throws JSONException
     */
    public void onlinefriends(HttpServletRequest request, HttpServletResponse response) throws IOException, FacebookException, JSONException, XMPPException {
        //get list of online friends.
        //get user id url prameter and get his/her online user files and parse it to jsonArray and send it call back it again
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        session = request.getSession();
        chatClient = (ChatClient) session.getAttribute("client");
        PrintWriter out = response.getWriter();
        if (chatClient == null) {
            String output = " <div id='fb-root'></div>"
                    + " <script src='http://connect.facebook.net/en_US/all.js'></script>"
                    + " <script> "
                    + "FB.init({appId  : '172430629459688',status : true,cookie : true,xfbml  : true }); "
                    + "FB.getLoginStatus(function(response) {"
                    + "    if (response.session) {"
                    + "      FB.logout();"
                    + "    } else {"
                    + "        "
                    + "} "
                    + "  });"
                    + "</script>";
            out.println(output);
            out.close();
            //output="FB.logout(function(response) {alert(JSON.stringify(response))});";
        } else {
            JSONArray jSONArray = chatClient.getOnlineUser();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject friend = jSONArray.getJSONObject(i);
                String to = "-";
                String friendId = friend.getString("uid");
                to += friend + "@chat.facebook.com";
                chatClient.sendMessage("", to);
            }
            jSONArray.write(response.getWriter());
            response.getWriter().close();
        }
    }

    /**
     * friendlist page is to send all friends with its name ,id and pic
     *
     *  nothing need to send via url as parameter
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws FacebookException
     * @throws JSONException
     */
    public void friendlist(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException, FacebookException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        session = request.getSession();
        chatClient = (ChatClient) session.getAttribute("client");
        PrintWriter out = response.getWriter();
        if (chatClient == null) {
            String output = " <div id='fb-root'></div>"
                    + " <script src='http://connect.facebook.net/en_US/all.js'></script>"
                    + " <script> "
                    + "FB.init({appId  : '172430629459688',status : true,cookie : true,xfbml  : true }); "
                    + "FB.getLoginStatus(function(response) {"
                    + "    if (response.session) {"
                    + "      FB.logout();"
                    + "    } else {"
                    + "        "
                    + "} "
                    + "  });"
                    + "</script>";
            out.println(output);
            out.close();
        } else {
            JSONArray jSONArray = chatClient.getBuddyList();
            jSONArray.write(response.getWriter());
            response.getWriter().close();
        }
    }
}
