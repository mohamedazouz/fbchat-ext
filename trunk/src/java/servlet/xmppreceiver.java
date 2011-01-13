/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import chat.ChatClient;
import chat.FriendBuddy;
import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.schema.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jivesoftware.smack.XMPPException;
import sun.net.www.http.HttpClient;

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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String apiKey = "76f98c6f348e8d27ed504ae74da69cea";
        String secretKey = "c4cc0e40e6f8f17362685640a9b0adb4";


        HttpSession session = request.getSession();
        request.getHeaderNames();
        String token = "";
        ChatClient c = null;



        /*System.out.println("LOGIN - Authentication Token created upon login: " + token);

        FacebookJsonRestClient frc = null;

        frc = new FacebookJsonRestClient(apiKey, secretKey);*/


        try {
            //frc.auth_getSession(token, false);
            //frc.auth_getSession(frc.auth_createToken());
            //String FB_SESSION_KEY = frc.auth_getSession(token);
            String login = (String) session.getAttribute("login");
            token = request.getParameter("auth_token");
            if (token != null) {
                c = new ChatClient();
                c.login(token);
                c.displayBuddyList();
                request.setAttribute("buddyList", c);
                c.disconnect();
            } else {
                session.setAttribute("login", "0");
                response.sendRedirect("http://www.facebook.com/login.php?api_key=76f98c6f348e8d27ed504ae74da69cea&v=1.0");
            }


            /* for (int i = 0; i < c.getList().size(); i++) {
            if (c.getList().get(i).getStaus().equals("1")) {
            output += "ID => " + c.getList().get(i).getId() + "</br>";
            output += "Name => " + c.getList().get(i).getName() + "</br>";
            output += "Status => " + c.getList().get(i).getStaus() + "</br>";
            output += "==================================================" + "</br>";
            }
            }*/


            //session.setAttribute("user", id);


//           // frc.notifications_sendEmailToCurrentUser("heello", "mazouz@activedd.com", "enta gamed ezy kda ?!");
//            Collection<Long> l = new ArrayList<Long>();
//            l.add(new Long("1198560721"));
//
//            frc.notifications_sendEmail(l, "hello", "hey");
//
//
//
//            //frc.sms_sendMessage(new Long("100001513410529"), "hello");
//            //request.getRequestDispatcher("index.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("hii");

            session.setAttribute("login", "1");
            //  response.sendRedirect(" http://www.facebook.com/login.php?api_key=" + apiKey + "&v=1.0");
            // response.sendRedirect(" http://www.facebook.com/login.php?api_key=" + apiKey + "&v=1.0");
            //http://www.facebook.com/login.php?api_key=76f98c6f348e8d27ed504ae74da69cea&v=1.0
        } finally {
            if (token != null) {
                session.setAttribute("login", "1");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            out.close();

        }
        /* try {

        if (sessionKey != null && sessionKey.length() > 0) {

        frc = new FacebookJsonRestClient(apiKey, secretKey, sessionKey);
        this.doTheThing(request, response, frc);

        } else {
        //        response.sendRedirect(" http://www.facebook.com/login.php?api_key=" + apiKey + "&v=1.0");
        }
        } catch (Exception e) {
        e.toString();
        }*/
        // request.getRequestDispatcher("index.jsp").forward(request, response);
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
        System.out.println("asdasd");
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

    private void doTheThing(HttpServletRequest request, HttpServletResponse response, FacebookJsonRestClient frc) {
    }
}
/*
 * session={"session_key":"2.s2Spip0GfyvaisDnrDBdAA__.3600.1294567200-100000015315523",
"uid":100000015315523,
"expires":1294567200,"secret":"_BcgtZaW6Skm_bMeWfCaXQ__","sig":"04ef5b0a2744969939c87f4b4ba79f42"}
 */
