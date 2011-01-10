/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.*;
import java.io.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket.ItemStatus;
import org.jivesoftware.smackx.packet.VCard;

/**
 *
 * @author Activedd2
 */
public class ChatClient implements MessageListener {

    XMPPConnection connection;
    SecurityMode securityMode = SecurityMode.enabled;
    boolean isSaslAuthenticationEnabled = true;
    boolean isCompressionEnabled = false;
    boolean isReconnectionAllowed = false;
    String sessionKey = "7f605feabdb3e1c7eb1efd43-1198560721";
    //   String sessionKey = "5d17b07a4eec1b6688d0aaed-100001513410529";
    String host = "69.63.181.104";
    int port = 5222;
    String domain = "chat.facebook.com";
    String apiKey = "76f98c6f348e8d27ed504ae74da69cea";
    String apiSecret = "c4cc0e40e6f8f17362685640a9b0adb4";
    String resource = "Facebook Group Chat";

    public void login(String userName, String password) throws XMPPException {

        SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM", FacebookConnectSASLMechanism.class);
        SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);

        ConnectionConfiguration config = new ConnectionConfiguration(domain, port);
        config.setSecurityMode(securityMode);
        config.setSASLAuthenticationEnabled(isSaslAuthenticationEnabled);
        config.setCompressionEnabled(isCompressionEnabled);
        config.setReconnectionAllowed(isReconnectionAllowed);

        //ConnectionConfiguration config = new ConnectionConfiguration("facebook.com");
        connection = new XMPPConnection(config);
        System.out.println("here");

        connection.connect();
        connection.login(apiKey + "|" + sessionKey, apiSecret, resource);
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
            if (presence.getType() == Presence.Type.available) {
                System.out.println(r.getName());
                System.out.println("ONLINE");
                Collection<RosterGroup> group = r.getGroups();
                for (RosterGroup g : group) {
                    System.out.println("group = >> " + g.getName());
                }
                System.out.println("----------------------");
            }

            
        }
        
    }

    public void disconnect() {
        connection.disconnect();
    }

    public void processMessage(Chat chat, Message message) {
        if (message.getType() == Message.Type.chat) {
            System.out.println(chat.getParticipant() + " says: " + message.getBody());
        }

    }

    public static void main(String args[]) throws XMPPException, IOException {
        // declare variables
        ChatClient c = new ChatClient();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg;


        // turn on the enhanced debugger
        // XMPPConnection.DEBUG_ENABLED = true;


        // provide your login information here
        c.login("azouz@chat.facebook.com", "7117340");
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


        c.disconnect();
        System.exit(0);
    }
}
