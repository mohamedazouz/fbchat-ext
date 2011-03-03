<%-- 
    Document   : thankYou
    Created on : Jan 24, 2011, 11:47:23 AM
    Author     : prog
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FaceBook Chat Login</title>
        <link rel="stylesheet" type="text/css" href="css/thank.css" />
        <script type="text/javascript">
            function setArabicCSS(){
                var lang=window.navigator.language;
                if(lang.indexOf("ar")!= -1){
                    if(lang == 'ar'){
                        var link=document.createElement("link");
                        link.setAttribute("href", "css/thank-rtl.css");
                        link.setAttribute("rel", "stylesheet");
                        link.setAttribute("type", "text/css");
                        document.getElementsByTagName("head")[0].appendChild(link);
                    }
                }
            }
        </script>
    </head>
    <body onload="setArabicCSS();">
        <div class="main-container">
            <div>
                <img src="images/thanks.png" width="232" height="69" alt="thanks" />
            </div>
            <div class="list f">
                <ul>
                    <li>saving many registers on the stack at once</li>
                    <li>moving large blocks of memory</li>
                    <li>complex and/or floating-point arithmetic</li>
                    <li>performing an atomic test-and-set instruction</li>
                    <li>instructions that combine ALU with an operand from memory rather than a register</li>
                    <li>complex and/or floating-point arithmetic</li>
                </ul>
            </div>
            <div class="f-r">
                <img src="images/facebook_image.png" width="521" height="287" />
            </div>
        </div>
    </body>
</html>
