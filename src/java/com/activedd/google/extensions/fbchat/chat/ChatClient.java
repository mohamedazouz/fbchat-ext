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
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.ProfileField;
import java.security.ProtectionDomain;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Activedd2
 *
 * this class is to manage whole chat client sending and receiving messages 
 */
public class ChatClient {

    //  private ArrayList<FriendBuddy> list = new ArrayList<FriendBuddy>();// Store All list of friends for login one
    private XMPPConnection connection; // Connect to facebook chat and also it implement all xmpp chat for fcebook
    private SecurityMode securityMode = SecurityMode.enabled;
    private boolean isSaslAuthenticationEnabled = true;
    private boolean isCompressionEnabled = false;
    private boolean isReconnectionAllowed = false;
    /**
     *
     */
    public static boolean connected = false;
    FacebookJsonRestClient facebook; // facebook client to get sessionkey and enable me to acces friends details like a photos and status
    MessageListenerImp messageListenerImp;
///media/D/Azouz/NetBeansProjects/proxy_facebook_chat/build/web/WEB-INF/classes/com/activedd/google/extensions/fbchat/chat/ChatClient.class"

    /**
     * this function connect to facebook via user authutocation token and get seesion key to login
     * @param FB_SESSION_KEY
     * @throws XMPPException
     * @throws InterruptedException
     * @throws FacebookException
     */
    public void login(String FB_SESSION_KEY, String apiKey, String apiSecret, String domain, String resource, int port) throws XMPPException, InterruptedException, FacebookException {
        try {
            SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM",
                    FacebookConnectSASLMechanism.class);
            SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);
            ConnectionConfiguration config = null;
            config = new ConnectionConfiguration(domain, port);
            config.setSecurityMode(securityMode);
            config.setSASLAuthenticationEnabled(isSaslAuthenticationEnabled);
            config.setCompressionEnabled(isCompressionEnabled);
            config.setReconnectionAllowed(isReconnectionAllowed);
            //config.
            connection = new XMPPConnection(config);
            messageListenerImp = new MessageListenerImp();
            connection.connect();
            facebook = new FacebookJsonRestClient(apiKey, apiSecret, FB_SESSION_KEY);
            connection.login(apiKey + "|" + FB_SESSION_KEY, apiSecret, resource);
            Presence packet = new Presence(Presence.Type.available);
            connection.sendPacket(packet);
            messageListenerImp.setTo(connection.getUser().split("/")[0]);
        } catch (Exception e) {
            System.out.println("error2");
        }
    }
    /*
     * to get all profile pictures for all users
     */

    public JSONArray getBuddyList() throws FacebookException, JSONException, FileNotFoundException {
        //ArrayList<FriendBuddy> list = new ArrayList<FriendBuddy>();

        ArrayList<Long> friendsID = new ArrayList<Long>();
        JSONArray friendsid = this.facebook.friends_get();
        for (int i = 0; i < friendsid.length(); i++) {
            Object object = friendsid.get(i);
            friendsID.add(new Long(object.toString()));
        }
        ArrayList<ProfileField> pf = new ArrayList<ProfileField>();
        pf.add(ProfileField.PIC_SQUARE);
        pf.add(ProfileField.NAME);
        friendsid = facebook.users_getInfo(friendsID, pf);
        return friendsid;
    }

    public JSONArray getOnlineUser() throws JSONException, FacebookException, FileNotFoundException {
        JSONArray friends = getBuddyList();
        JSONArray onlineFriends=new JSONArray();
        Roster roster = this.connection.getRoster();
        for (int i = 0; i < friends.length(); i++) {
            JSONObject jSONObject = friends.getJSONObject(i);
            Presence presence = roster.getPresence("-"+jSONObject.get("uid")+"@chat.facebook.com");
            if (presence.getType() == Presence.Type.available) {
                onlineFriends.put(jSONObject);
            }
            
        }
        return onlineFriends;
    }

    /**
     * to send message for spcific user
     * @param message
     * @param to
     * @throws XMPPException
     */
    public void sendMessage(String message, String to) throws XMPPException {
        Chat chat = connection.getChatManager().createChat(to, messageListenerImp);
        chat.sendMessage(message);
    }

    /**
     * to disconnect and logout from the server
     */
    public void disconnect() {
        connection.disconnect(new Presence(Presence.Type.unavailable));
    }
}
