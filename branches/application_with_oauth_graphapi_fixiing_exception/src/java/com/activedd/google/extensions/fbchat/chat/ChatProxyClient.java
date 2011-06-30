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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.RosterEntry;
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
    private Timer SchTimer = new Timer(); //timer thread for lossing session;

    public ChatProxyClient(ConnectionConfiguration config) {
        connection = new XMPPConnection(config);
        packetFilterImpl = new PacketFilterImp();
        packetListenerImp = new PacketListenerImp();
        SchTimer = this.StartTask();//rest timer
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
    public JSONObject xmppConnectAndLogin(String fbSessionKey, String apiKey, String apiSecret, String domain, String resource, int port) {
        String result = "";
        int resultValu = 1;
        try {
            if (!connection.isConnected()) {
                connection.connect();
            } else {
                if (connection.isConnected()) {
                    result += "+++ANA Da5lt Hena  bas gbt exception";
                    connection.login(apiKey + "|" + fbSessionKey, apiSecret); //login using user sessionkey as password.
                    connection.addPacketListener(packetListenerImp, packetFilterImpl);//adding the listening to listen for encoming packets
                } else {
                    result += "not connected";
                    resultValu = -1;
                }
            }
        } catch (Exception ex) {
            connection.disconnect();
            result += "+exception with login";
            resultValu = -1;
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
                if (presence.getType() == Presence.Type.available) {
                    String status = "";
                    if (roster.getPresence(user).getMode() == Presence.Mode.away) {
                        status = "away";
                    } else {
                        status = "online";
                    }
                    try {
                        friend.put("online", status);
                    } catch (JSONException ex) {
                    }
                    onlineFriends.put(friend);
                    friend = null;
                }

            }
        }
        SchTimer = this.StartTask();//rest timer
        return onlineFriends;
    }

    /**
     * to send message for specific user
     * @param message message text
     * @param to friend which message send to
     * @throws XMPPException
     */
    public void sendMessage(String msg, String to) {
        Message message = new Message();
        message.setBody(msg);
        message.setTo(to);
        connection.sendPacket(message);
        SchTimer = this.StartTask();//reset timer
    }

    /**
     * to disconnect and logout from the server
     */
    public void disconnect() {
        deleteUserChatFile();//delete user file.
        SchTimer.cancel();///cancel timer
        SchTimer.purge();
        if (connection.isConnected()) {
            connection.disconnect();//disconnect from facebook server
        }
    }

    public Timer StartTask() {
        int delay = 1000 * 60 * 7; //millisecondss
        SchTimer.cancel();
        SchTimer.purge();
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
            ProtectionDomain domain = this.getClass().getProtectionDomain();
            String path = domain.getCodeSource().getLocation().getPath();
            StringBuilder p_realPath = new StringBuilder(path.substring(path.indexOf("/"), path.indexOf("WEB-INF")));
            p_realPath.append("chat/").append(uid).append(".json");
            File file = new File(p_realPath.toString());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception ex) {
        }
    }
}
