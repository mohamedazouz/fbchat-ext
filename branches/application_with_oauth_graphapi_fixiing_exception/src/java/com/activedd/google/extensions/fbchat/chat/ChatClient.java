/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import com.google.code.facebookapi.FacebookException;
import java.util.*;
import java.io.*;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.ProfileField;
import java.security.ProtectionDomain;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Activedd2
 *
 * this class is to manage whole chat client from connection , login to facebook chat and  sending messages
 * @
 */
public final class ChatClient {

    private XMPPConnection connection; // Connect to facebook chat and also it implement all xmpp chat for fcebook
    private FacebookJsonRestClient facebook;
    // facebook client to get sessionkey and enable me to acces friends details like a photos and status
//    private MessageListenerImp messageListenerImp;
    private PacketFilterImp packetFilterImpl;
    private PacketListenerImp packetListenerImp;
    private Timer SchTimer = new Timer();

    public ChatClient(ConnectionConfiguration config) {
        connection = new XMPPConnection(config);
        packetFilterImpl = new PacketFilterImp();
        packetListenerImp = new PacketListenerImp();
        SchTimer = this.StartTask();
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
                if (connection.isConnected()) {
                    facebook = new FacebookJsonRestClient(apiKey, apiSecret, fbSessionKey);
                    connection.login(apiKey + "|" + fbSessionKey, apiSecret, resource);
                    connection.addPacketListener(packetListenerImp, packetFilterImpl);
                } else {
                    result += "+not connected";
                    resultValu = -1;
                }
            } else {
                result = "connected with errors";
                resultValu = 1;
            }

        } catch (XMPPException ex) {
            result = "exception with connecting";
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
     *
     * @return LoggedIn User Details
     * @throws FacebookException
     * @throws JSONException
     * @throws FileNotFoundException
     */
    public JSONObject getLoggedInUserDetails() throws FacebookException, JSONException, FileNotFoundException {
        ArrayList<Long> friendsID = new ArrayList<Long>();
        friendsID.add(facebook.users_getLoggedInUser());
        ArrayList<ProfileField> pf = new ArrayList<ProfileField>();
        pf.add(ProfileField.PIC_SQUARE);
        pf.add(ProfileField.NAME);
        JSONObject userdetail = facebook.users_getInfo(friendsID, pf).getJSONObject(0);
        return userdetail;
    }

    /**
     *
     * @return jsonArray contain all friends each with his/her {id,pic,name}
     * @throws FacebookException
     * @throws JSONException
     * @throws FileNotFoundException
     */
    public JSONArray getBuddyList() {
        ArrayList<Long> friendsID = new ArrayList<Long>();
        JSONArray friendsid = null;
        try {
            friendsid = this.facebook.friends_get();
        } catch (FacebookException ex) {
        }
        for (int i = 0; i < friendsid.length(); i++) {
            String friendId = null;
            try {
                friendId = friendsid.getString(i);
            } catch (JSONException ex) {
            }
            friendsID.add(new Long(friendId.toString()));
        }
        ArrayList<ProfileField> pf = new ArrayList<ProfileField>();
        pf.add(ProfileField.PIC_SQUARE);
        pf.add(ProfileField.NAME);
        try {
            friendsid = facebook.users_getInfo(friendsID, pf);
        } catch (FacebookException ex) {
        }

        SchTimer = this.StartTask();
        return friendsid;
    }

    /**
     *
     * @return jsonArray contain all online friends each with his/her {id,pic,name}
     * @throws JSONException
     * @throws FacebookException
     * @throws FileNotFoundException
     */
    public JSONArray getOnlineUser() {
        JSONArray friends = getBuddyList();
        JSONArray onlineFriends = new JSONArray();
        Roster roster = this.connection.getRoster();
        for (int i = 0; i < friends.length(); i++) {
            JSONObject jSONObject = null;
            try {
                jSONObject = friends.getJSONObject(i);
            } catch (JSONException ex) {
            }
            String user = null;
            try {
                user = "-" + jSONObject.get("uid") + "@chat.facebook.com";
            } catch (JSONException ex) {
            }
            Presence presence = roster.getPresence(user);
            if (presence.getType() == Presence.Type.available) {
                String status = "";
                if (roster.getPresence(user).getMode() == Presence.Mode.away) {
                    status = "away";
                } else {
                    status = "online";
                }
                try {
                    jSONObject.accumulate("online", status);
                } catch (JSONException ex) {
                }
                onlineFriends.put(jSONObject);
            }

        }
        SchTimer = this.StartTask();
        return onlineFriends;
    }

    public JSONArray getOnlineFriends() throws JSONException, FacebookException, FileNotFoundException {
        JSONArray onlineFriends = new JSONArray();
        Roster roster = connection.getRoster();
        Collection<RosterEntry> enteries = roster.getEntries();
        for (RosterEntry entry : enteries) {
            String user = entry.getUser();
            Presence presence = roster.getPresence(user);
            if (presence.getType() == Presence.Type.available) {
                JSONObject friend = new JSONObject();
                friend.put("uid", user.substring(1, entry.getUser().indexOf("@")));
                friend.put("name", entry.getName());
                if (presence.getType() == Presence.Type.available) {
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
        }
        SchTimer = this.StartTask();
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
        SchTimer = this.StartTask();
    }

    /**
     * to disconnect and logout from the server
     */
    public void disconnect() {
        deleteUserChatFile();
        SchTimer.cancel();
        SchTimer.purge();
        if (connection.isConnected()) {
            connection.disconnect();
        }
    }

    public void setIdle() {
        Presence packet = new Presence(Presence.Type.available);
        packet.setMode(Presence.Mode.away);
        connection.sendPacket(packet);
        SchTimer = this.StartTask();
    }

    public void setOnLinee() {
        Presence packet = new Presence(Presence.Type.available);
        packet.setMode(Presence.Mode.chat);
        connection.sendPacket(packet);
        SchTimer = this.StartTask();
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

    private void deleteUserChatFile() {
        try {
            String uid = (new Long(facebook.users_getLoggedInUser())).toString();
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
