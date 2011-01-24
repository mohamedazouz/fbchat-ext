/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import com.activedd.google.extensions.fbchat.chat.FriendBuddy;
import com.google.code.facebookapi.FacebookException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
     * 
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    public void send(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //TO DO: send a message,
        //get to ID url parameter and msg
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");
        try {
//            chatClient.s
//            request.setCharacterEncoding("UTF-8");//100001513410529 messaging/send.htm?to=100001513410529&msg=hello
            session = request.getSession();
            chatClient = (ChatClient) session.getAttribute("client");
            String to = "-";
            String friend = request.getParameter("to");
            to += friend + "@chat.facebook.com";
            String msg = request.getParameter("msg");
            chatClient.sendMessage(msg, to);
        } catch (Exception ex) {
            
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws FacebookException
     * @throws JSONException
     */
    public void onlinefriends(HttpServletRequest request, HttpServletResponse response) throws IOException, FacebookException, JSONException {
        //TO DO: get list of online friends.
        //get user id url prameter and get his/her online user files and parse it to jsonArray and send it call back it again

        JSONArray jSONArray = new JSONArray();
        response.setContentType("application/json;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        session = request.getSession();
        chatClient = (ChatClient) session.getAttribute("client");
        //String userId = request.getParameter("userid");
        ArrayList<FriendBuddy> list = (ArrayList<FriendBuddy>) chatClient.getOnlineUser();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", list.get(i).getId());
            jSONObject.put("name", list.get(i).getName());
            jSONObject.put("pic", list.get(i).getPic());
            jSONArray.put(jSONObject);
        }
        jSONArray.write(response.getWriter());
//        out.println(jSONArray.toString());
        response.getWriter().close();
    }

    /**
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws JSONException
     */
    public void friendlist(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        JSONArray jSONArray = new JSONArray();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //String userId = request.getParameter("userid");
        session = request.getSession();
        chatClient = (ChatClient) session.getAttribute("client");
        ArrayList<FriendBuddy> list = (ArrayList<FriendBuddy>) chatClient.displayBuddyList();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", list.get(i).getId());
            jSONObject.put("name", list.get(i).getName());
            jSONObject.put("pic", list.get(i).getPic());
            jSONArray.put(jSONObject);
        }
        jSONArray.write(response.getWriter());
//        out.println(jSONArray.toString());
        response.getWriter().close();
        //TO DO: get a list of all friends in json.
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
}
