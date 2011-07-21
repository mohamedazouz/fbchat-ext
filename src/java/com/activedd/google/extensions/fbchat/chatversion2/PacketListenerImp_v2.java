/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chatversion2;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 *
 * @author ibrahim
 */
public class PacketListenerImp_v2 implements PacketListener {

    private JsonCreate_v2 jsonCreate;

    public PacketListenerImp_v2(JsonCreate_v2 jsonCreate) {
        this.jsonCreate = jsonCreate;
    }

    public void processPacket(Packet packet) {
        if (packet.toString().contains("Message")) {
            Message message = (Message) packet;
            try {
                jsonCreate.createJsonFile(message.getFrom(), message.getBody(), message.getTo().split("/")[1]);
            } catch (Exception ex) {
                if (!(ex instanceof NullPointerException)) {
                }
            }
        }
    }
}
