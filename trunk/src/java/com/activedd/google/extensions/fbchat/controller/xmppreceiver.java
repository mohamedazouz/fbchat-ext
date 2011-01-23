/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import com.activedd.google.extensions.fbchat.chat.ChatClient;
import com.activedd.google.extensions.fbchat.chat.FriendBuddy;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Activedd2
 */
public class xmppreceiver extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
     * this function is the home of the extension which enable user to login
     * by getting token to pass it to login function in out client
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        request.getHeaderNames();
        String token = "";
        ChatClient c = null;
        try {
           
            if (session.getAttribute("buddList") == null) {
                ChatClient.connected = false;
            }
            if (!ChatClient.connected) { 
                token = request.getParameter("auth_token");
                if (token != null) {
                    c = new ChatClient();
                    c.login(token);
                    c.displayBuddyList();
                    session.setAttribute("buddList", c);
                    //   c.disconnect();
                } else {
                    session.setAttribute("loginApp", "1");
                    String firstApplicationloging = "http://www.facebook.com/login.php?api_key=172430629459688&connect_display=popup&v=1.0&next=http://196.221.190.148:8084/fbchatproxy/&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access";
                    String redirct = "http://www.facebook.com/login.php?api_key=76f98c6f348e8d27ed504ae74da69cea&v=1.0";
                    redirct = "http://www.facebook.com/login.php?api_key=172430629459688&connect_display=popup&v=1.0&next=http://www.facebook.com/login.php?api_key=76f98c6f348e8d27ed504ae74da69cea&v=1.0/&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access";
                    response.sendRedirect(redirct);
                    
                }
            }
        } catch (Exception e) {
            System.out.println("error");
            /*
             * XMPPError connecting to chat.facebook.com:5222.: remote-server-error(502) XMPPError connecting to chat.facebook.com:5222.
             *-- caused by: java.net.ConnectException: Connection timed out: connect
             */
        } finally {
            if (token != null) {
                request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
            }
            out.close();

        }


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>   
}
/*
https://graph.facebook.com/oauth/authorize?client_id=172430629459688&redirect_uri=http://41.178.64.38:8080/Facebook/&scope=user_photos,user_videos,publish_stream,status_update,xmpp_login,access_token
perission URL

-https://graph.facebook.com/oauth/authorize?client_id=172430629459688&redirect_uri=http://41.178.64.38:8080/Facebook/&scope=user_photos,user_videos,publish_stream,status_update,xmpp_login,access_token,email

-
http://www.facebook.com/login.php?api_key=172430629459688&connect_display=popup&v=1.0&next=http://196.221.190.148:8084/fbchatproxy/&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access

-https://graph.facebook.com/oauth/authorize?client_id=172430629459688&redirect_uri=http://41.178.64.38:8080/Facebook/scope=user_photos,user_videos,publish_stream

session form =
session={"session_key":"2.B3MstcDUG2nSaJSiemxBDg__.3600.1294934400-100000015315523",
"uid":100000015315523,"expires":1294934400,"secret":"3A12HRGXJqPsqvDMWeqw_A__",
"sig":"a4bd86aeb40eb9f7bf86221929605cc0"}
 */
