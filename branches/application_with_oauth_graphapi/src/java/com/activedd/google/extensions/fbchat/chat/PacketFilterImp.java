/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 *
 * @author ibrahim
 */
public class PacketFilterImp implements PacketFilter {

    public boolean accept(Packet packet) {
        if (packet.toString().contains("Message")) {
            Message message = (Message) packet;
            if (message.getBody() == null) {
                return false;
            }
        }
        return true;
    }
}
