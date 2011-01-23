/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import com.google.code.facebookapi.FacebookException;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.ProfileField;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Activedd2
 *
 * this class is to manage whole chat client sending and receiving messages 
 */
public class ChatClient implements MessageListener {

    private ArrayList<FriendBuddy> list = new ArrayList<FriendBuddy>();// Store All list of friends for login one
    private XMPPConnection connection; // Connect to facebook chat and also it implement all xmpp chat for fcebook
    private SecurityMode securityMode = SecurityMode.enabled;
    private boolean isSaslAuthenticationEnabled = true;
    private boolean isCompressionEnabled = false;
    private boolean isReconnectionAllowed = false;
    private int port = 5222;
    private String domain = "chat.facebook.com";
    
    private String apiKey = "76f98c6f348e8d27ed504ae74da69cea";  //Application Key
    private String apiSecret = "c4cc0e40e6f8f17362685640a9b0adb4";  //Application Secert key
    private String resource = "Facebook Group Chat";
    public static boolean connected = false;
    FacebookJsonRestClient facebook; // facebook client to get sessionkey and enable me to acces friends details like a photos and status
    HashMap<Long, String> profilepictures = new HashMap<Long, String>();

    /*
     * this function connect to facebook via user authutocation token and get seesion key to login
     */
    public void login(String token) throws XMPPException, InterruptedException, FacebookException {
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
            connection = new XMPPConnection(config);
            connection.connect();
            facebook = new FacebookJsonRestClient(apiKey, apiSecret);
            String FB_SESSION_KEY = facebook.auth_getSession(token);
            connection.login(apiKey + "|" + FB_SESSION_KEY, apiSecret, resource);
            Presence packet = new Presence(Presence.Type.available);
            connection.sendPacket(packet);
            GetprofilesPictures();
        } catch (Exception e) {
            System.out.println("error2");
        }
    }
/*
 * to get all profile pictures for all users
 */
    void GetprofilesPictures() {
        try {
            ArrayList<Long> friendsID = new ArrayList<Long>();
            JSONArray friendsid = facebook.friends_get();
            for (int i = 0; i < friendsid.length(); i++) {
                Object object = friendsid.get(i);
                friendsID.add(new Long(object.toString()));
            }
            ArrayList<ProfileField> pf = new ArrayList<ProfileField>();
            pf.add(ProfileField.PIC);
            pf.add(ProfileField.NAME);
            friendsid = facebook.users_getInfo(friendsID, pf);
            for (int i = 0; i < friendsid.length(); i++) {
                JSONObject jSONObject = friendsid.getJSONObject(i);
                String pic = (String) jSONObject.get("pic");
                String id = ((Object) jSONObject.get("uid")).toString();
                profilepictures.put(new Long(id), pic);
            }
            File file = new File("/media/D/Azouz/NetBeansProjects/proxy_facebook_chat/web/recentchat/all-" + connection.getUser().subSequence(1, connection.getUser().lastIndexOf("@")) + ".json");
            PrintWriter out;
            out = new PrintWriter(file);
            out.append(friendsid.toString(5));
            out.close();

        } catch (Exception ex) {
            System.out.println(ex.toString());

        }

    }
/*
 * get profile picture for user id
 */
    String getprofilepic(long id) {
        String pic = profilepictures.get(id);
        return pic;
    }

    public void loginkaman() throws XMPPException, InterruptedException, FacebookException {
        ConnectionConfiguration config = null;
        config = new ConnectionConfiguration("", port);

        config.setSecurityMode(securityMode);
        config.setSASLAuthenticationEnabled(isSaslAuthenticationEnabled);
        config.setCompressionEnabled(isCompressionEnabled);
        config.setReconnectionAllowed(isReconnectionAllowed);

        connection = new XMPPConnection(config);



        String FB_SESSION_KEY = "7f605feabdb3e1c7eb1efd43-1198560721";
        try {
            connection.connect();

            facebook = new FacebookJsonRestClient(apiKey, apiSecret, FB_SESSION_KEY);
            facebook.isDesktop();
            facebook.users_setStatus("ya moshel wel application  dah y5las b2aa :D");

            String username = apiKey + "|" + FB_SESSION_KEY;
            connection.login(username, apiSecret);
            /*
             * XMPPError connecting to :5222.: remote-server-error(502) XMPPError connecting to :5222.
            -- caused by: java.net.ConnectException: Connection refused
             */
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void loginagain(String username, String pass) {
        try {
            SASLAuthentication.registerSASLMechanism("DIGEST-MD5", MySASLDigestMD5Mechanism.class);

            ConnectionConfiguration config = new ConnectionConfiguration("chat.facebook.com", 5222);
            //config.setSASLAuthenticationEnabled(true);

            connection = new XMPPConnection(config);

            connection.connect();
            connection.login(username, pass);
        } catch (Exception e) {
            System.out.println("hi");
        }

    }
/*
 * to send message for spcific user
 */
    public void sendMessage(String message, String to) throws XMPPException {
        Chat chat = connection.getChatManager().createChat(to, this);
        chat.sendMessage(message);
    }

    public void displayBuddyList() {
        try {
            list.clear();
            Roster roster = connection.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();
            JSONArray jSONArray = new JSONArray();
            for (RosterEntry r : entries) {
                Presence presence = roster.getPresence(r.getUser());
                FriendBuddy friend = new FriendBuddy();
                friend.setId(r.getUser());

                friend.setName(r.getName());
                String temp = r.getUser();
                String id = (String) temp.subSequence(1, temp.lastIndexOf("@"));
                friend.setPic(getprofilepic(new Long(id)));
                if (presence.getType() == Presence.Type.available) {
                    friend.setStaus("1");
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("id", friend.getId().subSequence(1, friend.getId().lastIndexOf("@")));
                    jSONObject.put("name", friend.getName());
                    jSONArray.put(jSONObject);
                } else {
                    friend.setStaus("0");
                }
                list.add(friend);
            }
            File file = new File("/media/D/Azouz/NetBeansProjects/proxy_facebook_chat/web/recentchat/online-" + connection.getUser().subSequence(1, connection.getUser().lastIndexOf("@")) + ".json");
            PrintWriter out;
            out = new PrintWriter(file);
            String json = jSONArray.toString(5);
            out.append(json);
            out.close();

        } catch (Exception ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /*
     * to disconnect and logout from the server
     */
    public void disconnect() {
        connection.disconnect();
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        try {

            if (message.getType() == Message.Type.chat && message.getBody() != null) {
                System.out.println("xml:" + message.toXML());
                System.out.println(chat.getParticipant() + " says: " + message.getBody() + " to :" + connection.getUser());
                CreatJsonFile c = new CreatJsonFile();
                c.createJsonFile(chat.getParticipant(), message.getBody());
            }
        } catch (Exception ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]) throws XMPPException, IOException, InterruptedException, FacebookException {

        ChatClient c = new ChatClient();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg;
        String sessionKey = "7f605feabdb3e1c7eb1efd43-1198560721";
        c.loginkaman();
        c.displayBuddyList();
        System.out.println("-----");
        System.out.println("Enter your message in the console.");
        System.out.println("-----\n");
        for (int i = 0; i < c.getList().size(); i++) {
            System.out.println(c.getList().get(i).getName());
        }
        c.disconnect();
    }

    public void signout() {
        Presence packet = new Presence(Presence.Type.unavailable);
        connection.sendPacket(packet);
        connection.disconnect();
        ChatClient.connected = false;
    }
    public void goOffline() {
        Presence packet = new Presence(Presence.Type.available);
        packet.setMode(Presence.Mode.away);
        connection.sendPacket(packet);
    }


    /**
     * @return the list
     */
    public ArrayList<FriendBuddy> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList<FriendBuddy> list) {
        this.list = list;
    }
}
