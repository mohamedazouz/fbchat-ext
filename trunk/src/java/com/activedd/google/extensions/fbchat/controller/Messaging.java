/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import com.activedd.google.extensions.fbchat.chat.FriendBuddy;
import com.google.code.facebookapi.FacebookException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jivesoftware.smack.ChatManager;
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

    public void send(HttpServletRequest request, HttpServletResponse response) {
        //TO DO: send a message,
        //get to ID url parameter and msg
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
//            chatClient.s
            request.setCharacterEncoding("UTF-8");//100001513410529 messaging/send.htm?to=100001513410529&msg=hello
            session = request.getSession();
            chatClient = (ChatClient) session.getAttribute("client");
            String to = "-";
            String friend = request.getParameter("to");
            to += friend + "@chat.facebook.com";
            String msg = request.getParameter("msg");
            chatClient.sendMessage(msg, to);
        } catch (Exception ex) {
            Logger.getLogger(ReadyChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onlinefriends(HttpServletRequest request, HttpServletResponse response) throws IOException, FacebookException, JSONException {
        //TO DO: get list of online friends.
        //get user id url prameter and get his/her online user files and parse it to jsonArray and send it call back it again

        JSONArray jSONArray = new JSONArray();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
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
        out.println(jSONArray.toString());
    }

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
        out.println(jSONArray.toString());
        //TO DO: get a list of all friends in json.
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
}
