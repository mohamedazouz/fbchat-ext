/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatProxyClient;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class NewMessaging extends MultiActionController {

    HttpSession session;
    private ChatProxyClient chatClient;

    /**
     * send page is to send message to specific user.
     *
     * url need two parameter of sending user in key named "to" , message text in key named "msg"
     *
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    public void send(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, XMPPException {
        //send a message
        //get to ID url parameter and msg
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        session = request.getSession();
        chatClient = (ChatProxyClient) session.getAttribute("client");
        int flg = 1;
        if (chatClient == null || !chatClient.isConnected()) {
            flg = -1;
        } else {
            StringBuilder toId = new StringBuilder();
            String friend = request.getParameter("to");
            toId.append("-").append(friend).append("@chat.facebook.com");
            JSONObject jSONObject = chatClient.sendMessage(request.getParameter("msg"), toId.toString());
            if (jSONObject.has("errorstatus")) {
                flg = -1;
            }
        }
        if (flg == -1) {
            JSONObject jSONObject;
            try {
                jSONObject = new JSONObject("{status: 400 ,message:'No Session Found'}");
                jSONObject.write(response.getWriter());
                response.getWriter().close();
            } catch (Exception ex) {
            }
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
    public void onlinefriends(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, JSONException, FileNotFoundException {
        //get list of online friends.
        //get user id url prameter and get his/her online user files and parse it to jsonArray and send it call back it again
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        session = request.getSession();
        chatClient = (ChatProxyClient) session.getAttribute("client");
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject("{status: 400 ,message:'No Session Found'}");
        } catch (JSONException ex) {
        }
        JSONArray jSONArray = new JSONArray();
        int flg = 1;
        if (chatClient == null || !chatClient.isConnected()) {
            flg = -1;
        } else {
            jSONArray = chatClient.getOnlineFriends();
            if (jSONArray.getJSONObject(0).has("errorstatus")) {
                flg = -1;
            }
        }
        if (flg == -1) {
            jSONArray.put(jSONObject);
        }
        try {
            jSONArray.write(response.getWriter());
            response.getWriter().close();
        } catch (Exception ex) {
        }


    }
}
