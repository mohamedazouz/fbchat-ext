/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.json.JSONException;

/**
 *
 * @author ibrahim
 */
public class PacketListenerImp implements PacketListener {

    private JsonCreate jsonCreate = new JsonCreate();

    public void processPacket(Packet packet) {
        if (packet.toString().contains("Message")) {
            Message message = (Message) packet;
            try {
                jsonCreate.createJsonFile(message.getFrom(), message.getBody(), message.getTo().split("/")[0]);
            } catch (Exception ex) {
                if(!(ex instanceof NullPointerException)){
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
