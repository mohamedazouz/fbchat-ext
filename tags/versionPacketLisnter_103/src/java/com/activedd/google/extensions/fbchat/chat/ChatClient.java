/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import com.google.code.facebookapi.FacebookException;
import java.util.*;
import java.io.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.ProfileField;
import org.jivesoftware.smack.ChatManager;
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
//        messageListenerImp = new MessageListenerImp();
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
    public void xmppConnectAndLogin(String fbSessionKey, String apiKey, String apiSecret, String domain, String resource, int port) throws XMPPException, InterruptedException, FacebookException {
        connection.connect();
        facebook = new FacebookJsonRestClient(apiKey, apiSecret, fbSessionKey);
        connection.login(apiKey + "|" + fbSessionKey, apiSecret, resource);
        connection.addPacketListener(packetListenerImp, packetFilterImpl);
        //Presence packet = new Presence(Presence.Type.available);
        // connection.sendPacket(packet);
        //messageListenerImp.setTo(connection.getUser().split("/")[0]);

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
    public JSONArray getBuddyList() throws FacebookException, JSONException, FileNotFoundException {
        ArrayList<Long> friendsID = new ArrayList<Long>();
        JSONArray friendsid = this.facebook.friends_get();
        for (int i = 0; i < friendsid.length(); i++) {
            String friendId = friendsid.getString(i);
            //ChatManager chatmanager = connection.getChatManager();
            //  Chat newChat = chatmanager.createChat("-" + friendId + "@chat.facebook.com", messageListenerImp);
            friendsID.add(new Long(friendId.toString()));
        }
        ArrayList<ProfileField> pf = new ArrayList<ProfileField>();
        pf.add(ProfileField.PIC_SQUARE);
        pf.add(ProfileField.NAME);
        friendsid = facebook.users_getInfo(friendsID, pf);
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
    public JSONArray getOnlineUser() throws JSONException, FacebookException, FileNotFoundException {
        JSONArray friends = getBuddyList();
        JSONArray onlineFriends = new JSONArray();
        Roster roster = this.connection.getRoster();
        for (int i = 0; i < friends.length(); i++) {
            JSONObject jSONObject = friends.getJSONObject(i);
            Presence presence = roster.getPresence("-" + jSONObject.get("uid") + "@chat.facebook.com");
            if (presence.getType() == Presence.Type.available) {
                onlineFriends.put(jSONObject);
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
    public void sendMessage(String msg, String to) throws XMPPException {
//        Chat chat = connection.getChatManager().createChat(to, messageListenerImp);
        //Chat chat = connection.getChatManager().createChat(to, null);
        //chat.sendMessage(message);
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
        /**Presence packet = new Presence(Presence.Type.unavailable);
        connection.sendPacket(packet);
        connection.disconnect();*/
        Presence packet = new Presence(Presence.Type.unavailable);
        SchTimer.cancel();
        SchTimer.purge();
        connection.disconnect(packet);
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
        int delay = 1000 * 60 * 15; //millisecondss
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
}
