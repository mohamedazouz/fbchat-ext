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
                        document.getElementById("en-doc").style.display='none';
                        document.getElementById("ar-doc").style.display='block';
                        document.getElementById("thanksIMG").src='images/thanks_ar.png';
                    }
                }
            }
        </script>
    </head>
    <body onload="setArabicCSS();">
        <div class="main-container">
            <div>
                <img id="thanksIMG" src="images/thanks.png" width="232" height="69" alt="thanks" />
            </div>
            <div class="list f" id="en-doc">
                <ul>
                    <li>Click on the FB Chat extension icon next to the URL bar</li>
                    <li>The extension will redirect you to the Facebook for authentication</li>
                    <li>After logging in you will find two tabs</li>
                    <li>The online friends tab will show your online friends</li>
                    <li>The friends list tab will show you all your friends</li>
                    <li>You can send a massages to your offline friends , and they will see them in their Facebook inbox </li>
                </ul>
            </div>
            <div class="list f" id="ar-doc" style="display: none;">
                <ul>
                    <li>قم بالضغط على أيقونة الإضافة التي بجانب شريط العنوان</li>
                    <li>الاضافة ستقوم بتوجيهك لل FaceBook للتوثيق</li>
                    <li>بعد تسجيل الدخول ستجد نافذتان</li>
                    <li>نافذة الأصدقاء المتصلين تقوم بإظهار أصدقائك المتصلين حاليا</li>
                    <li>نافذة قائمة الأصدقاء تقوم بإظهار جميع أصدقائك</li>
                    <li>
                        يمكنك إرسال رسائل للأصدقاء الغير متصلين حاليا وستصلهم في صندوق بريدهم على ال FaceBook
                    </li>
                </ul>
            </div>
            <div class="f-r">
                <img src="images/facebook_image.png" width="521" height="287" />
            </div>
        </div>
    </body>
</html>
