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
    </head>
    <%--<jsp:useBean id="buddList" scope="session" class="chat.ChatClient" />--%>

    <body>

        <a href="Signout">Sign out</a>
        <a href="updatelist">UpdateList</a>
        <a href="users.jsp">Users</a>
        <a href="onlineUsers.jsp">online</a>
        <form action="Login" method="post">
            User Name :<input type="text" name="username">
            Password :<input type="password" name="password">
            <input type="submit">
        </form>
        <form action="ReadyChat" method="post">
            <div id="chooseform">
                <select id="friend" name="friend">
                    <c:forEach items="${buddList.list}" var="l">
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
        </form>
    </body>
</html>


