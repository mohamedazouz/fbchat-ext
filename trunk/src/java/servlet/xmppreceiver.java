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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
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
            if (session.getAttribute("buddList2")==null) {
                ChatClient.connected = false;
            }
            if (!ChatClient.connected) {
                String login = (String) session.getAttribute("login");
                token = request.getParameter("auth_token");
                if (token != null) {
                    c = new ChatClient();
                    c.login(token);
                    c.displayBuddyList();
                    session.setAttribute("buddList2", c);
                    request.setAttribute("buddyList", c);
                    //   c.disconnect();
                } else {
                    session.setAttribute("login", "0");
                    String redirct = "http://www.facebook.com/login.php?api_key=172430629459688&connect_display=popup&v=1.0&next=http://www.facebook.com/connect/login_success.html&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access";
                    redirct = "http://www.facebook.com/login.php?api_key=76f98c6f348e8d27ed504ae74da69cea&v=1.0";
                    response.sendRedirect(redirct);

                }
            } else {
                c = (ChatClient) session.getAttribute("buddList2");
                request.setAttribute("buddyList", c);
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
https://graph.facebook.com/oauth/authorize?client_id=172430629459688&redirect_uri=http://41.178.64.38:8080/Facebook/&scope=user_photos,user_videos,publish_stream,status_update,xmpp_login,access_token

perission URL
https://graph.facebook.com/oauth/authorize?client_id=172430629459688&redirect_uri=http://41.178.64.38:8080/Facebook/&scope=user_photos,user_videos,publish_stream,status_update,xmpp_login,access_token,email

http://www.facebook.com/login.php?api_key=172430629459688&connect_display=popup&v=1.0&next=http://www.facebook.com/connect/login_success.html&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access

 *
 https://graph.facebook.com/oauth/authorize?client_id=172430629459688&redirect_uri=http://41.178.64.38:8080/Facebook/scope=user_photos,user_videos,publish_stream

session={"session_key":"2.B3MstcDUG2nSaJSiemxBDg__.3600.1294934400-100000015315523",
"uid":100000015315523,"expires":1294934400,"secret":"3A12HRGXJqPsqvDMWeqw_A__",
"sig":"a4bd86aeb40eb9f7bf86221929605cc0"}


session={
"session_key"%3A"5d17b07a4eec1b6688d0aaed-100001513410529"%2C"
uid"%3A100001513410529%2C"expires"%3A0%2C
"secret"%3A"3cfcd6bec73c9186ce5d4b9302a63502"%2C
"sig"%3A"84adc32ac86e6689201a1377c6a01113"}-->
<!--session={
"session_key":"5d17b07a4eec1b6688d0aaed-100001513410529",
"uid": 100001513410529,
"expires":0,
"secret":"3cfcd6bec73c9186ce5d4b9302a63502",
"sig"%3A"84adc32ac86e6689201a1377c6a01113"}
session={"session_key":"7f605feabdb3e1c7eb1efd43-1198560721","uid":1198560721,"expires":0,"secret":"399a564062f403d117ce1fcdb446b333","sig":"a7f5162e5e738f5970ec627a29b929a0"}

{
   "algorithm": "HMAC-SHA256",
   "expires": 1294146000,
   "issued_at": 1294141497,
   "oauth_token": "113869198637480|2.GFZ3o1nGIbxex7Tx1XsqrQ__.3600.1294146000-100001513410529|6PEBPjigmH_bkr--gjVXWycl1tw",
   "registration": {
      "name": "Active Johnny",
      "birthday": "11\/17\/1977",
      "gender": "male",
      "location": {
         "name": "Cairo, Egypt",
         "id": 115351105145884
      },
      "email": "fbapps@activedd.com"
   },
   "registration_metadata": {
      "fields": "name,birthday,gender,location,email"
   },
   "user": {
      "locale": "en_GB",
      "country": "eg"
   },
   "user_id": "100001513410529"
}


{
   "signed_request": "AmX2Cd27Ck3v-Btr9B273wSVK_uXRNV3qRUYZzd6abU.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImV4cGlyZXMiOjEyOTQxNDYwMDAsImlzc3VlZF9hdCI6MTI5NDE0MTQ5Nywib2F1dGhfdG9rZW4iOiIxMTM4NjkxOTg2Mzc0ODB8Mi5HRlozbzFuR0lieGV4N1R4MVhzcXJRX18uMzYwMC4xMjk0MTQ2MDAwLTEwMDAwMTUxMzQxMDUyOXw2UEVCUGppZ21IX2Jrci0tZ2pWWFd5Y2wxdHciLCJyZWdpc3RyYXRpb24iOnsibmFtZSI6IkFjdGl2ZSBKb2hubnkiLCJiaXJ0aGRheSI6IjExXC8xN1wvMTk3NyIsImdlbmRlciI6Im1hbGUiLCJsb2NhdGlvbiI6eyJuYW1lIjoiQ2Fpcm8sIEVneXB0IiwiaWQiOjExNTM1MTEwNTE0NTg4NH0sImVtYWlsIjoiZmJhcHBzQGFjdGl2ZWRkLmNvbSJ9LCJyZWdpc3RyYXRpb25fbWV0YWRhdGEiOnsiZmllbGRzIjoibmFtZSxiaXJ0aGRheSxnZW5kZXIsbG9jYXRpb24sZW1haWwifSwidXNlciI6eyJsb2NhbGUiOiJlbl9HQiIsImNvdW50cnkiOiJlZyJ9LCJ1c2VyX2lkIjoiMTAwMDAxNTEzNDEwNTI5In0"
}
*/
