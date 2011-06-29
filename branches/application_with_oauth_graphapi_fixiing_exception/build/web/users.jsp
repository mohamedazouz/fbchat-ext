<%-- 
    Document   : Chat
    Created on : 11/01/2011, 12:55:35 م
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

    <body>
        <h1>YOur UsErS!</h1>
        <a href="index.jsp">back</a>
        <form action="ReadyChat" method="post">
            <div id="chooseform">
                
                    <c:forEach items="${buddList.list}" var="l">
                        <div >
                            pic : <img src="${l.pic}" />
                            Name : ${l.name}
                            status : ${l.staus}
                        </div>
                    </c:forEach>
                    
                <button id="choose" onclick="getchat();">chat</button>
            </div>

            <div id="chatarea">
                <textarea id="chatext" name="chatext"></textarea>
            </div>
            <input type="submit" id="choose" value="chat">
        </form>

    </body>
</html>
