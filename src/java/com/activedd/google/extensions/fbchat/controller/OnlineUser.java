/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author ibrahim
 */
public class OnlineUser extends HttpServlet {

    JSONArray jSONArray;
    String redirct ;
    String userId ;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
     * this function to get all online friends now on facebook
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if (request.getParameter("userid") != null) {
                userId = request.getParameter("userid");
                redirct="/OnlineUser?online=";
            }
            if (request.getParameter("online") != null) {
                redirct = "";
                out.print(request.getParameter("online"));
            } else {
                File file = new File("/media/D/Azouz/NetBeansProjects/proxy_facebook_chat/web/recentchat/online-" + userId + ".json");
                Scanner sc = new Scanner(file);
                String temp = "";
                while (sc.hasNextLine()) {
                    temp += sc.nextLine();
                }
                try {
                    jSONArray = new JSONArray(temp);
                } catch (JSONException ex) {
                    Logger.getLogger(OnlineUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            if (!redirct.equals("")) {
                redirct+=jSONArray.toString();
                request.getRequestDispatcher(redirct).forward(request, response);
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