<%-- 
    Document   : logout
    Created on : Mar 2, 2011, 3:42:08 PM
    Author     : ibrahim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="fb-root"></div>
        <script src="http://connect.facebook.net/en_US/all.js"></script>
        <script>
            FB.init({appId  : '172430629459688',status : true,cookie : true,xfbml  : true }); 
            FB.getLoginStatus(function(response) {
                if (response.session) {
                    FB.logout();
                }
            });
        </script>
    </body>
</html>
