/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trails;

import chat.FacebookConnectSASLMechanism;
import chat.FriendBuddy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

public class SessionHandlerImpl {

    String sessionKey;
    private String apiKey = "76f98c6f348e8d27ed504ae74da69cea";
    private String apiSecret = "c4cc0e40e6f8f17362685640a9b0adb4";
    int port = 5222;
    String domain = "chat.facebook.com";
    String resource = "Facebook Group Chat";
    SecurityMode securityMode = SecurityMode.enabled;
    boolean isSaslAuthenticationEnabled = true;
    boolean isCompressionEnabled = false;
    boolean isReconnectionAllowed = false;
    XMPPConnection xmppConnection;
    private ArrayList<FriendBuddy> list = new ArrayList<FriendBuddy>();

    public void startSession(String sessionKey) {
        this.sessionKey = sessionKey;

        SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM",
                FacebookConnectSASLMechanism.class);
        SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);

        ConnectionConfiguration config = null;

        config = new ConnectionConfiguration(domain,port);

        config.setSecurityMode(securityMode);
        config.setSASLAuthenticationEnabled(isSaslAuthenticationEnabled);
        config.setCompressionEnabled(isCompressionEnabled);
        config.setReconnectionAllowed(isReconnectionAllowed);

        xmppConnection = new XMPPConnection(config);

        try {
            xmppConnection.connect();
        } catch (XMPPException e) {
            System.out.println("error");
        }

        try {
            xmppConnection.login(apiKey + "|" + sessionKey, apiSecret, resource);
        } catch (XMPPException e) {
            System.out.println("error2");
        }
    }
      public void displayBuddyList() {
        Roster roster = xmppConnection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();

        for (RosterEntry r : entries) {
            Presence presence = roster.getPresence(r.getUser());
            FriendBuddy friend = new FriendBuddy();
            friend.setId(r.getUser());
           
            friend.setName(r.getName());
            if (presence.getType() == Presence.Type.available) {
                friend.setStaus("1");
            } else {
                friend.setStaus("0");
            }
            getList().add(friend);
        }

    }

   public static void main(String []args){
       SessionHandlerImpl impl=new SessionHandlerImpl();
       impl.startSession("7f605feabdb3e1c7eb1efd43-1198560721");
       impl.displayBuddyList();
        for (int i = 0; i < impl.getList().size(); i++) {
            System.out.println(impl.getList().get(i).getName());
        }
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
