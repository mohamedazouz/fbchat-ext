<%-- 
    Document   : index
    Created on : 03/01/2011, 01:42:21 م
    Author     : Activedd2
--%>


<%@page import="com.google.apphosting.utils.remoteapi.RemoteApiPb.Response"%>
<%@page import="com.google.appengine.repackaged.com.google.protobuf.ByteString.Output"%>
<%@page import="chat.ChatClient"%>
<%@page import="com.google.code.facebookapi.FacebookApiUrls"%>
<%@page import="javax.xml.ws.spi.http.HttpContext"%>
<%@page import="servlet.XMPPTrails"%>
<%@page import="com.google.appengine.api.xmpp.SendResponse"%>
<%@page import="com.google.appengine.api.xmpp.SendResponse"%>
<%@page import="com.google.appengine.api.xmpp.XMPPServiceFactory"%>
<%@page import="com.google.appengine.api.xmpp.XMPPService"%>
<%@page import="com.google.appengine.api.xmpp.MessageBuilder"%>
<%@page import="com.google.appengine.api.xmpp.Message"%>
<%@page import="com.google.appengine.api.xmpp.JID"%>
<%@page import="java.util.Map"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.code.facebookapi.ProfileField"%>
<%@page import="com.google.code.facebookapi.ProfileFieldItem"%>
<%@page import="com.google.code.facebookapi.FacebookMethod"%>
<%@page import="com.google.code.facebookapi.Permission"%>
<%@page import="com.google.code.facebookapi.FacebookJsonRestClient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <title>JSP Page</title>
        <script type="text/javascript"   src="http://code.jquery.com/jquery-1.4.4.js"></script>
        <script type="text/javascript" src="js/jquery.jfeed.js"></script>
        <script type="text/javascript" >
            /*$(document).ready(function(){
                alert("hi")
              $("#chatarea").hide();
            })
            
            function getchat(){
                 $("#chatarea").show();
                x=document.getElementById("friend").value;
                alert(x);
                $("#chooseform").hide();
            }
            function sendmessage(){
                $("#chooseform").show();
                $("#chatarea").hide();
            }*/
        </script>
    </head>
    <jsp:useBean id="buddyList" scope="request" class="chat.ChatClient"></jsp:useBean>

    <body>


        <%
                    //ADRAHI
                   /* String text = "From My App: HELLO WORLD";
                    String FB_APP_API_KEY = new String("76f98c6f348e8d27ed504ae74da69cea");
                    String FB_APP_SECRET = new String("c4cc0e40e6f8f17362685640a9b0adb4");
                    String FB_SESSION_KEY = (String) session.getAttribute("user");
                    if (FB_SESSION_KEY.equals(null) || FB_SESSION_KEY.equals("")) {
                    System.out.println("hi");
                    }
                    //String FB_SESSION_KEY = new String("5d17b07a4eec1b6688d0aaed-100001513410529");
                    //FB_SESSION_KEY=HttpContext.Current.Request.Cookies["YOUR_APP_KEY" + "_session_key"].Value;
                    //String FB_SESSION_KEY = new String("7f605feabdb3e1c7eb1efd43-1198560721");
                    // String FB_userId = new String("1198560721");
                    Long fb = new Long(FB_SESSION_KEY.split("-")[1]);

                    //long fb = 1198560721;
                    FacebookMethod fm = FacebookMethod.AUTH_CREATE_TOKEN;
                    FacebookApiUrls apiUrls = new FacebookApiUrls();
                    apiUrls.getDefaultHttpsServerUrl();
                    FacebookJsonRestClient facebook = new FacebookJsonRestClient(FB_APP_API_KEY, FB_APP_SECRET, FB_SESSION_KEY);

                    //FacebookJsonRestClient facebookClient2 = (FacebookJsonRestClient)facebook.getFacebookRestClient();
                    FacebookJsonRestClient facebookClient = (FacebookJsonRestClient) facebook;
                    /*if(facebookClient.users_hasAppPermission(Permission.valueOf('xmpp_login')))
                    {

                    }*/
                    //long x = facebookClient.users_getLoggedInUser();

                    /*                    String output = "HELLO Mr.";


                    ArrayList<ProfileField> pflist = new ArrayList<ProfileField>();
                    pflist.add(ProfileField.FIRST_NAME);
                    pflist.add(ProfileField.LAST_NAME);
                    pflist.add(ProfileField.ACTIVITIES);
                    ArrayList<Long> lists = new ArrayList<Long>();
                    lists.add(fb);
                    JSONArray array = facebookClient.users_getInfo(lists, pflist);
                    for (int i = 0; i < array.length(); i++) {
                    JSONObject jSONObject = array.getJSONObject(i);
                    output+=jSONObject.toString();
                    output += jSONObject.get("first_name").toString() + " ";
                    output += jSONObject.get("last_name").toString();
                    }

                    /*  JSONArray array = facebookClient.friends_get(fb);
                    ArrayList<ProfileField> pflist = new ArrayList<ProfileField>();
                    pflist.add(ProfileField.FIRST_NAME);
                    pflist.add(ProfileField.LAST_NAME);
                    ArrayList<Long> lists = new ArrayList<Long>();
                    String allinone = "<br>";
                    for (int i = 0; i < array.length(); i++) {

                    lists.add(new Long(array.getString(i)));
                    }
                    array = facebookClient.users_getInfo(lists, pflist);
                    allinone += "this my friends -> " + array.length() + "<br>";
                    allinone += "    First Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Name <br>";
                    for (int i = 0; i < array.length(); i++) {
                    JSONObject jSONObject = array.getJSONObject(i);//+ "     " + array.toString(2)+"  <br>";
                    allinone += jSONObject.get("first_name") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + jSONObject.get("last_name") + "<br>";

                    }*/

                    /*FacebookMethod  mm=new FacebookMethod();

                    String s=p.authorizationUrl(FB_APP_API_KEY, );

                    Permission.SHARE_ITEM
                    facebookClient.users_setStatus("Hello From my first Apps", true, fb);

                    facebookClient.stream_publish(text, null, null, null, null);
                    facebookClient.links_post(fb,"http:www.youm7.com","its from my first Application");

                    facebook.users_setStatus("HELLO WORLD FROM MY FIRST APP ON FACEBOOK ;)", fb);
                    ProfileField pf = ProfileField.ABOUT_ME;
                    ArrayList<ProfileField> pflist = new ArrayList<ProfileField>();
                    pflist.add(ProfileField.ABOUT_ME);
                    pflist.add(ProfileField.FIRST_NAME);
                    pflist.add(ProfileField.SEX);
                    pflist.add(ProfileField.STATUS);
                    ArrayList<Long> lists = new ArrayList<Long>();
                    lists.add(fb);
                    JSONArray array=facebookClient.users_getInfo(lists, pflist);
                    JSONObject json=new JSONObject();
                    json.accumulate("message", "ezyak");
                    facebookClient.liveMessage_send(fb, "Oops",json);
                     */
                    /* JSONArray array = facebookClient.friends_get(fb);
                    ArrayList<ProfileField> pflist = new ArrayList<ProfileField>();
                    pflist.add(ProfileField.FIRST_NAME);
                    pflist.add(ProfileField.LAST_NAME);
                    ArrayList<Long> lists = new ArrayList<Long>();
                    String allinone = "<br>";
                    for (int i = 0; i < array.length(); i++) {

                    lists.add(new Long(array.getString(i)));
                    }
                    array = facebookClient.users_getInfo(lists, pflist);
                    allinone += "this my friends -> " + array.length() + "<br>";
                    allinone += "    First Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Name <br>";
                    for (int i = 0; i < array.length(); i++) {
                    JSONObject jSONObject = array.getJSONObject(i);//+ "     " + array.toString(2)+"  <br>";
                    allinone += jSONObject.get("first_name") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + jSONObject.get("last_name") + "<br>";

                    }*/
                    /*Message m=new Message();
                    m.setSender("Mohamedaliazouz@gmail.com");
                    m.setSubject("helo");
                    m.setTextBody("ezyak");
                    m.setTo("m_aliazouz@yahoo.com");*/
                    //JSONArray array = facebookClient.photos_getAlbums(fb);
                    //JSONArray array = facebookClient.friends_get();
                    //facebookClient.(fb, m);
                    //facebookClient.users_setStatus("its my first status from APPICATION ';_)");
                    /*output = "HELLO WORLD";
                    XMPPTrails xmpp = new XMPPTrails();
                    output=xmpp.send();*/
                    // output = (new Long(55)).toString();
                    String output = "Your Online User" + "</br>";
                    /*  String FB_SESSION_KEY = (String) session.getAttribute("user");

                    ;
                    System.out.println("successfully updated");
                    ChatClient c = new ChatClient();

                    c.login(FB_SESSION_KEY);
                    c.displayBuddyList();

                    for (int i = 0; i < c.getList().size(); i++) {
                    if (c.getList().get(i).getStaus().equals("1")) {
                    output += "ID => " + c.getList().get(i).getId() + "</br>";
                    output += "Name => " + c.getList().get(i).getName() + "</br>";
                    output += "Status => " + c.getList().get(i).getStaus() + "</br>";
                    output += "==================================================" + "</br>";
                    }
                    }
                    c.disconnect();*/
        %>


        <a href="Signout">Sign out</a>
        <%= output%>

        <!--        http://www.facebook.com/login.php?api_key=76f98c6f348e8d27ed504ae74da69cea&v=1.0-->
        <form action="Login" method="post">
            User Name :<input type="text" name="username">
            Password :<input type="password" name="password">
            <input type="submit">
        </form>
        <form action="ReadyChat" method="post">
            <div id="chooseform">
                <select id="friend" name="friend">
                    <c:forEach items="${buddyList.list}" var="l">
                        <c:if test="${l.staus == '1'}">

                            <option value="${l.id}">${l.name}</option>
                        </c:if>
                        <%--<c:out value="${l.staus}"></c:out>

                        --%>
                    </c:forEach>
                </select>
                <button id="choose" onclick="getchat();">chat</button>
            </div>

            <div id="chatarea">
                <textarea id="chatext" name="chatext"></textarea>
            </div>
            <input type="submit" id="choose" value="chat">

            <%
                        String rcv = "tet";
                        session.setAttribute("chat", buddyList);


                        /*  new Thread(new Runnable() {

                        @Override
                        public void run() {
                        if (!(buddyList.getRcvedMessage().equals("")) || !(buddyList.getRcvedMessage() == null)) {
                        buddyList.getRcvedMessage();
                        }
                        }
                        });*/
            %>
            <%=rcv%>


        </form>
    </body>
</html>


