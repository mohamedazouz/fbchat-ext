/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.MessageType;
import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

/**
 *
 * @author Activedd2
 */
public class XMPPTrails {

    public String send() {
        String output = "-1";
        JID jidfrom = new JID("azouz@chat.facebook.com");
        // JID jidto = new JID("azouz@chat.facebook.com");

        String msgBody = "Someone has sent you a gift on Example.com. To view: http://example.com/gifts/";
        Message msg = new MessageBuilder().withRecipientJids(jidfrom).withMessageType(MessageType.NORMAL).withBody(msgBody).build();

        boolean messageSent = false;

        XMPPService xmpp = XMPPServiceFactory.getXMPPService();
        try {
            Presence p = xmpp.getPresence(jidfrom);
            if (p.isAvailable()) {
                SendResponse status = xmpp.sendMessage(msg);
                messageSent = (status.getStatusMap().get(jidfrom) == SendResponse.Status.SUCCESS);
            }
        } catch (Exception e) {
            return "ya wksa !!";
        }
        if (!messageSent) {
            // Send an email message instead...
            output = "Wrong";
        }
        return output;
    }
}
