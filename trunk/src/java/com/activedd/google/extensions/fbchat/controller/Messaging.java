/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author prog
 */
public class Messaging extends MultiActionController {

    HttpSession session;

    public void send(HttpServletRequest request, HttpServletResponse response) {
        //TO DO: send a message,
        //get to ID url parameter and msg
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
            session = request.getSession();
            String to = "-";
            String friend = request.getParameter("to");
            if (!friend.contains("@")) {
                to += friend + "@chat.facebook.com";
            } else {
                to = friend;
            }
            String msg = request.getParameter("msg");
            ChatClient c = (ChatClient) session.getAttribute("buddList");
            c.sendMessage(msg, to);
        } catch (Exception ex) {
            Logger.getLogger(ReadyChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onlinefriends(HttpServletRequest request, HttpServletResponse response) {
        //TO DO: get list of online friends.
        //get user id url prameter and get his/her online user files and parse it to jsonArray and send it call back it again 
        JSONArray jSONArray = new JSONArray();
        String redirct = "";
        String userId = "";
        response.setContentType("application/json;charset=UTF-8");
        try {
            if (request.getParameter("userid") != null) {
                userId = request.getParameter("userid");
                redirct = "/OnlineUser?online=";
            }
            if (request.getParameter("online") != null) {
                redirct = "";
            } else {
                File file = new File("/media/D/Azouz/NetBeansProjects/proxy_facebook_chat/web/recentchat/online-" + userId + ".json");
                Scanner sc = new Scanner(file);
                String temp = "";
                while (sc.hasNextLine()) {
                    temp += sc.nextLine();
                }

                jSONArray = new JSONArray(temp);

            }
            if (!redirct.equals("")) {
                redirct += jSONArray.toString();
                request.getRequestDispatcher(redirct).forward(request, response);
            }
        } catch (Exception ex) {
//            Logger.getLogger(OnlineUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void friendlist(HttpServletRequest request, HttpServletResponse response){
        //TO DO: get a list of all friends in json.
    }
}
