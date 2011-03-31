
*****************************************************************************
*Welcome to facebook chat client.                                           *
*simply chat client enable your to use facebook chat through some api.      *
*									    *
/***************************************************************************
version 106 
based on packet listen which listen to all packets moved to logined user
also it detect if the user online or away status
its much light more than 101



Prerequest
***********
you should just create an appilcation in facebook and put your application data (application id , application key , application secretkey).
then you put it in "fbproperties.properties" which included in WEB-INF folder.
notice : you must not to change domain , resource , port in the file.




Instruction important links
*****************************
1- /login/login.htm to authnicate user through facebook response thanks page
2- /login/getauthkey.htm to get your session key the response will be json object {sessionkey=XXXXXXXXXX}
3- /connect/connect.htm?sessionkey=XXXXXXXXXX to connect to facebook chat response json object represting user facebook name ,profile picture and user ID
4- /connect/disconnect to disconnect and logout from chat.
5- /Messaging/friendlist.htm  get all friends with facebook name ,profile picture and user ID
6- /Messaging/onlinefriends.htm get all online friends with facebook name ,profile picture and user ID
7- /Messaging/send.htm?msg=messageContent&to=FRIEND_ID  to send message to friends_ID





