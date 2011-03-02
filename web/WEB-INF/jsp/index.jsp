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
        <script type="text/javascript" src="http://connect.facebook.net/en_US/all.js" ></script>
    </head>


    <body>
        Welcome Page
       <!-- <div id="fb-root"></div>
        <script src="http://connect.facebook.net/en_US/all.js"></script>
        <script>
            FB.init({
                appId  : '172430629459688',
                status : true, // check login status
                cookie : true, // enable cookies to allow the server to access the session
                xfbml  : true  // parse XFBML
            });
            FB.getLoginStatus(function(response) {
                if (response.session) {
                    alert("you should signout")
                } else {
                    alert("you should loging")
                }
            });
        </script>
        <button onclick="FB.logout(function(response) {
            alert(JSON.stringify(response))
        });">logout</button>
        <button onclick="FB.login(function(response) {
        if (response.session) {
            alert('done')
        } else {
            alert('pone')
        }
    });">login</button>-->
    </body>
</html>


