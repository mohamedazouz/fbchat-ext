/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 *
 * @author prog
 */
public class MessageListenerImp implements MessageListener {

    public void processMessage(Chat chat, Message message) {
        try {
            if (message.getType() == Message.Type.chat && message.getBody() != null) {
                jsonCreate.createJsonFile(chat.getParticipant(), message.getBody());
            }
        } catch (Exception ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JsonCreate jsonCreate;

    public void setJsonCreate(JsonCreate jsonCreate) {
        this.jsonCreate = jsonCreate;
    }
}
