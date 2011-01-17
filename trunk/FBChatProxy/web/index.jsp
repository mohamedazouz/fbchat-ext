<%-- 
    Document   : index
    Created on : 03/01/2011, 01:42:21 م
    Author     : Activedd2
--%>


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

        <a href="Signout">Sign out</a>
        

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


