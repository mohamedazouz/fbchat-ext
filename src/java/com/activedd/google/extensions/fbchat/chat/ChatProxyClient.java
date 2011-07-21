/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import java.util.*;
import java.io.*;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import java.security.ProtectionDomain;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.packet.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Activedd2
 *
 * this class is to manage whole chat client from connection , login to facebook chat server
 * ,sending messages and retrieving online friends.
 * @
 */
public final class ChatProxyClient {

    private XMPPConnection connection; // Connect to facebook chat and also it implement all xmpp chat for fceboo
    private PacketFilterImp packetFilterImpl;//  filter message packet which contian no body
    private PacketListenerImp packetListenerImp;//listen for incomming messages and store in user file.
    private Timer SchTimer; //timer thread for lossing session;s
    int sessionTimeOut = 4;
    private String realPath;
    
    public ChatProxyClient(ConnectionConfiguration config, String realPath) {
        connection = new XMPPConnection(config);
        packetFilterImpl = new PacketFilterImp();
        JsonCreate jsonCreate=new JsonCreate();
        jsonCreate.setRealPath(realPath);
        packetListenerImp = new PacketListenerImp(jsonCreate);
        this.realPath = jsonCreate.getRealPath();
    }

    /**
     * connect facebook chat host  using XMPP protocol which facebook provide for its server, implemented by smack API
     *
     * login to facebook  using X-FACEBOOK-PLATFORM SASL authentication mechanism implemented by smack API
     *
     * @param fbSessionKey its user session key for this application
     * @param apiKey application key
     * @param apiSecret application secret key
     * @param domain it is facebook chat domain
     * @param resource Facebook Group Chat
     * @param port connection port to facebook
     * @throws XMPPException
     * @throws InterruptedException
     * @throws FacebookException
     */
    public JSONObject xmppConnectAndLogin(String fbSessionKey, String apiKey, String apiSecret, String domain, String resource, int port, String apiID, int sessionTimeOut) {
        String result = "";
        int resultValu = 1;
        //connection.addPacketListener(packetListenerImp, packetFilterImpl);//adding the listening to listen for encoming packets
        try {
            if (!connection.isConnected()) {

                connection.connect();
                if (connection.isConnected()) {
                    try {
                        connection.login(apiID + "|" + fbSessionKey, apiSecret); //login using user sessionkey as password.
                        connection.addPacketListener(packetListenerImp, packetFilterImpl);
                        this.sessionTimeOut = sessionTimeOut;
                        result = "connected";
                        SchTimer = this.StartTask();
                    } catch (Exception e) {
                        result += "error  logining";
                        resultValu = -1;
                        this.endTimer();
                    }
                } else {
                    result = "not connected";
                    resultValu = -1;
                    this.endTimer();
                }
            } else {
                result = "connected with errors";
                resultValu = -1;
                this.endTimer();
            }
        } catch (Exception ex) {
            result += "+exception with login" + ex.getMessage();
            resultValu = -1;
            this.endTimer();
        } finally {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("msg", result);
                jSONObject.put("status", resultValu);
            } catch (JSONException ex) {
            }
            return jSONObject;
        }
    }

    /**
     *
     * @return jsonArray contain all online friends each with his/her {id,name}
     * @throws JSONException
     * @throws FacebookException
     * @throws FileNotFoundException
     */
    public JSONArray getOnlineFriends() {
        JSONArray onlineFriends = new JSONArray();//online friends container
        try {
            Roster roster = connection.getRoster();//get roster connection which contain all about friend
            Collection<RosterEntry> enteries = roster.getEntries();//friends Entries
            for (RosterEntry entry : enteries) {//loop for check online frineds
                String user = entry.getUser();//friend id
                Presence presence = roster.getPresence(user);
                if (presence.getType() == Presence.Type.available) {//check if friend is not offline
                    JSONObject friend = new JSONObject();
                    try {
                        friend.put("uid", user.substring(1, entry.getUser().indexOf("@")));
                    } catch (JSONException ex) {
                    }
                    try {
                        friend.put("name", entry.getName());
                    } catch (JSONException ex) {
                    }
                    String status = "";
                    if (roster.getPresence(user).getMode() == Presence.Mode.away) {
                        status = "away";
                    } else {
                        status = "online";
                    }
                    friend.put("online", status);
                    onlineFriends.put(friend);
                    friend = null;
                }
            }
            SchTimer = this.StartTask();//rest timer
        } catch (Exception e) {
            this.endTimer();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("msg", e.getMessage());
                jSONObject.put("errorstatus", -1);
            } catch (JSONException ex) {
            }
            onlineFriends.put(jSONObject);
        } finally {
            return onlineFriends;
        }

    }

    /**
     * to send message for specific user
     * @param message message text
     * @param to friend which message send to
     * @throws XMPPException
     */
    public JSONObject sendMessage(String msg, String to) {
        JSONObject jSONObject = new JSONObject();
        try {
            Message message = new Message();
            message.setBody(msg);
            message.setTo(to);
            connection.sendPacket(message);
            SchTimer = this.StartTask();//reset timer
        } catch (Exception e) {
            this.endTimer();
            try {
                jSONObject.put("msg", e.getMessage());
                jSONObject.put("errorstatus", -1);
            } catch (JSONException ex) {
            }
        } finally {
            return jSONObject;
        }
    }

    /**
     * to disconnect and logout from the server
     */
    public void disconnect() {
        deleteUserChatFile();//delete user file.
        if (connection != null) {
            if (connection.isConnected()) {
                connection.disconnect();//disconnect from facebook server
            }
            connection.removePacketListener(packetListenerImp);
        }
        try {
            SchTimer.cancel();///cancel timer
            SchTimer.purge();
        } catch (Exception e) {
        }

        SchTimer = null;
        System.gc();
    }

    public Timer StartTask() {
        int delay = 1000 * 60 * sessionTimeOut; //millisecondss
        if (SchTimer != null) {
            SchTimer.cancel();///cancel timer
            SchTimer.purge();
        }
        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                disconnect();
                timer.cancel();
                timer.purge();
            }
        }, delay);
        return timer;
    }

    private void endTimer() {
        if (SchTimer != null) {
            SchTimer.cancel();///cancel timer
            SchTimer.purge();
        }
        disconnect();
    }

    public boolean isConnected() {
        return connection.isConnected();
    }

    /**
     * delete user file which is "userID".json
     * get userID from connection user then delete it.
     */
    private void deleteUserChatFile() {
        try {
            String user = connection.getUser();//user id
            String uid = user.substring(1, user.indexOf("@"));
//            ProtectionDomain domain = this.getClass().getProtectionDomain();
//            String path = domain.getCodeSource().getLocation().getPath();
//            StringBuilder p_realPath = new StringBuilder(path.substring(path.indexOf("/"), path.indexOf("WEB-INF")));
//            p_realPath.append("chat/").append(uid).append(".json");
            File file = new File(realPath + uid + ".json");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception ex) {
        }
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public static void main(String args[]) {
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        // XMPPConnection.DEBUG_ENABLED = true;
                        final SecurityMode securityMode = SecurityMode.enabled;
                        final boolean isSaslAuthenticationEnabled = true;
                        final boolean isCompressionEnabled = false;
                        final boolean isReconnectionAllowed = false;
                        ConnectionConfiguration config = null;
                        String appID = "102201119868199";
                        String appKey = "b6028f531b11c157e9ab42815cd2a433";
                        String apiSecretkey = "d5756c1d7291c62bc16372e3112b6060";
                        String server = "chat.facebook.com";
                        String sessionKey = "102201119868199|5fcdf506afb30378e55cc64b.1-1198560721|arE2qGJCTjT1iOd0n_GugOGe2_Y";
                        sessionKey = sessionKey.substring(sessionKey.indexOf("|") + 1, sessionKey.lastIndexOf("|"));
                        int port = 5222;
                        //access_token=127410177333318|c1878e53d815eacb850bd07e.1-1198560721|s8GRlMP7IQGuoivM6qoEK6TScYo
                        String userName = appID + "|" + sessionKey;
                        SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM", FacebookConnectSASLMechanism.class);
                        SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);
                        config = new ConnectionConfiguration(server, port);
                        JsonCreate create=new JsonCreate();
                        create.setRealPath("/home/ibrahim/Desktop/");
                        ChatProxyClient chatProxyClient = new ChatProxyClient(config,"/home/ibrahim/Desktop/");
                        JSONObject jSONObject = chatProxyClient.xmppConnectAndLogin(sessionKey, appKey, apiSecretkey, server, "eshta", port, appID, 4);
                        System.out.println(jSONObject.toString(5));
                       //System.out.println(chatProxyClient.sendMessage("hello", "-100002298398209@chat.facebook.com").toString(5));;
                        //   System.out.println(chatProxyClient.getOnlineFriends().toString(5));
                       // chatProxyClient.disconnect();
                    } catch (Exception ex) {
                    }
                }
            }).start();
        }
    }
    
}