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
 *
 * message receiver listener
 */
public class MessageListenerImp implements MessageListener {

    /**
     * Message receiver
     */
    private String to;

    /**
     * automatic called when user receive message and call CreateJson class to store it
     *
     * @param chat who send the message
     * @param message receiving message
     */
    public void processMessage(Chat chat, Message message) {
        try {



            if (message.getType() == Message.Type.chat && message.getBody() != null) {
//                setJsonCreate(new JsonCreate());
                jsonCreate.createJsonFile(chat.getParticipant(), message.getBody(), to);
            }
        } catch (Exception ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private JsonCreate jsonCreate =new JsonCreate();

//    public void setJsonCreate(JsonCreate jsonCreate) {
//        this.jsonCreate = jsonCreate;
//    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the jsonCreate
     */
//    public JsonCreate getJsonCreate() {
//        return jsonCreate;
//    }
}
