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
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" >
            function ready(){
                document.getElementById("chatarea").disabled=true; 
            }
            function getchat(){
                document.getElementById("chatarea").disabled=false;

                x=document.getElementById("friend").value;
                alert(x);
                
                document.getElementById("chooseform").disable=true;
            }
            function sendmessage(){
                alert(document.getElementById("chatext").value)
                document.getElementById("chooseform").disabled=false;
                document.getElementById("chatarea").disabled=true;
            }
        </script>
    </head>
    <jsp:useBean id="buddyList" scope="request" class="chat.ChatClient"></jsp:useBean>

    <body onload="ready()">


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
                        
                        /*
                        new Thread(new Runnable() {

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
<!--
https://graph.facebook.com/oauth/authorize?client_id=172430629459688&redirect_uri=http://41.178.64.38:8080/Facebook/&scope=user_photos,user_videos,publish_stream,status_update,xmpp_login,access_token

perission URL
https://graph.facebook.com/oauth/authorize?client_id=172430629459688&redirect_uri=http://41.178.64.38:8080/Facebook/&scope=user_photos,user_videos,publish_stream,status_update,xmpp_login,access_token,email

http://www.facebook.com/login.php?api_key=172430629459688&connect_display=popup&v=1.0&next=http://www.facebook.com/connect/login_success.html&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&session_key_only=true&req_perms=user_photos,user_videos,publish_stream,status_update,xmpp_login,offline_access


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
-->

