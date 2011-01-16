/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

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
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import chat.FriendBuddy;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.schema.Friendlist;
import com.google.code.facebookapi.schema.StreamLikes.Friends;
import org.hibernate.validator.util.GetMethod;
import sun.net.www.http.HttpClient;

/**
 *
 * @author Activedd2
 */
public class ChatClient implements MessageListener {

    private ArrayList<FriendBuddy> list = new ArrayList<FriendBuddy>();
    private XMPPConnection connection;
    private SecurityMode securityMode = SecurityMode.enabled;
    private boolean isSaslAuthenticationEnabled = true;
    private boolean isCompressionEnabled = false;
    private boolean isReconnectionAllowed = false;
    // private String sessionKey = "7f605feabdb3e1c7eb1efd43-1198560721";
    //   String sessionKey = "5d17b07a4eec1b6688d0aaed-100001513410529";
    private int port = 5222;
    String host = "41.178.64.38";
    private String domain = "chat.facebook.com";
    private String apiKey = "76f98c6f348e8d27ed504ae74da69cea";
    private String apiSecret = "c4cc0e40e6f8f17362685640a9b0adb4";
    private String resource = "Facebook Group Chat";
    private String rcvedMessage = "";
    public static boolean connected = false;

    public void login(String token) throws XMPPException, InterruptedException, FacebookException {

        SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM", FacebookConnectSASLMechanism.class);
        SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);

        ConnectionConfiguration config = new ConnectionConfiguration(domain, port);
        config.setSecurityMode(securityMode);
        config.setSASLAuthenticationEnabled(isSaslAuthenticationEnabled);
        config.setCompressionEnabled(isCompressionEnabled);
        config.setReconnectionAllowed(isReconnectionAllowed);

        //ConnectionConfiguration config = new ConnectionConfiguration("facebook.com");

        connection = new XMPPConnection(config);
        connection.connect();
        String FB_SESSION_KEY;
        try {
            FacebookJsonRestClient facebook = new FacebookJsonRestClient(apiKey, apiSecret);
            FB_SESSION_KEY = facebook.auth_getSession(token, true);
            FB_SESSION_KEY.length();
            String username = apiKey + "|" + FB_SESSION_KEY;
            connection.login(username, apiSecret);
            if (!(connection.getUser().equals("") || connection.getUser().equals(null))) {
                ChatClient.connected = true;
            }
        } catch (Exception e) {
            System.out.println("");
        }

//.rqtfSG5Oni9te9NmjCvs_A__.3600.1294923600-1062791510"
        //"2.3pJn88_LZYi0QxP_Vb8JrQ__.3600.1294923600-100000015315523"
        // 5d17b07a4eec1b6688d0aaed-100001513410529

        //FB_SESSION_KEY = "7f605feabdb3e1c7eb1efd43-1198560721";azouz
        //FB_SESSION_KEY = "5d17b07a4eec1b6688d0aaed-100001513410529";//johny
        //connection.login(userName, password);

    }

    public void loginagain(String username, String pass) throws XMPPException, InterruptedException, FacebookException {

        //SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM", FacebookConnectSASLMechanism.class);
        //SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);
        SASLAuthentication.registerSASLMechanism("DIGEST-MD5", MySASLDigestMD5Mechanism.class);
        // ConnectionConfiguration config = new ConnectionConfiguration(domain, port);
        ConnectionConfiguration config = new ConnectionConfiguration("chat.facebook.com", 5222);
        connection = new XMPPConnection(config);

        connection.connect();
        connection.login(username, pass);
        /*config.setSecurityMode(securityMode);
        config.setSASLAuthenticationEnabled(isSaslAuthenticationEnabled);
        config.setCompressionEnabled(isCompressionEnabled);
        config.setReconnectionAllowed(isReconnectionAllowed);

        //ConnectionConfiguration config = new ConnectionConfiguration("facebook.com");

        connection = new XMPPConnection(config);
        connection.connect();

        FacebookJsonRestClient frc = null;

        frc = new FacebookJsonRestClient(apiKey, apiSecret);
        //        String FB_SESSION_KEY = frc.auth_getSession(token);


        String FB_SESSION_KEY = "7f605feabdb3e1c7eb1efd43-1198560721";

        connection.login(apiKey + "|" + FB_SESSION_KEY, apiSecret, resource);*/
        //connection.login(userName, password);

    }

    public void sendMessage(String message, String to) throws XMPPException {
        Chat chat = connection.getChatManager().createChat(to, this);
        chat.sendMessage(message);
    }

    public void displayBuddyList() {
        Roster roster = connection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();


        System.out.println("\n\n" + entries.size() + " buddy(ies):");

        for (RosterEntry r : entries) {
            // System.out.println(r.getUser());
            Presence presence = roster.getPresence(r.getUser());
            FriendBuddy friend = new FriendBuddy();
            friend.setId(r.getUser());

            friend.setName(r.getName());
            if(r.getName().equalsIgnoreCase("Prog Mania"))
            {
                System.out.println("hi");
            }
            if (presence.getType() == Presence.Type.available) {
                /*System.out.println(r.getName());
                System.out.println("ONLINE");
                Collection<RosterGroup> group = r.getGroups();
                for (RosterGroup g : group) {
                System.out.println("group = >> " + g.getName());
                }
                System.out.println("----------------------");*/
                friend.setStaus("1");
            } else {
                friend.setStaus("0");
            }
            list.add(friend);
            //list.add(friend);
        }

    }

    public void disconnect() {
        connection.disconnect();
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        if (message.getType() == Message.Type.chat && message.getBody() != null) {

            System.out.println("xml:"+message.toXML());
            System.out.println(chat.getParticipant() + " says: " + message.getBody() + " to :"+connection.getUser());
            CreatXMLFile c = new CreatXMLFile();
            try {
                c.createXML(chat.getParticipant(), connection.getUser().split("/")[0], message.getBody());
            } catch (Exception ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            setRcvedMessage(message.getBody());
        }

    }

    public static void main(String args[]) throws XMPPException, IOException, InterruptedException, FacebookException {
        // declare variables
        ChatClient c = new ChatClient();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg;


        // turn on the enhanced debugger
        // XMPPConnection.DEBUG_ENABLED = true;


        // provide your login information here
        String sessionKey = "7f605feabdb3e1c7eb1efd43-1198560721";
        c.login(sessionKey);
        //c.login("1198560721@chat.facebook.com", "7117340");


        c.displayBuddyList();

        System.out.println("-----");
        System.out.println("Enter your message in the console.");
        System.out.println("-----\n");

        while (!(msg = br.readLine()).equals("bye")) {
            // your buddy's gmail address goes here
            //c.sendMessage(msg, "-1062791510@chat.facebook.com");
            c.sendMessage(msg, "-100001513410529@chat.facebook.com");
        }
        /*  for (int i = 0; i < c.getList().size(); i++) {
        if (c.getList().get(i).getStaus().equals("1")) {
        System.out.println("ID => " + c.getList().get(i).getId());
        System.out.println("Name => " + c.getList().get(i).getName());
        System.out.println("Status => " + c.getList().get(i).getStaus());
        System.out.println("==================================================");
        }
        }*/
        c.disconnect();
        // System.exit(0);
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

    /**
     * @return the rcvedMessage
     */
    public String getRcvedMessage() {
        return rcvedMessage;
    }

    /**
     * @param rcvedMessage the rcvedMessage to set
     */
    public void setRcvedMessage(String rcvedMessage) {
        this.rcvedMessage = rcvedMessage;
    }
}
