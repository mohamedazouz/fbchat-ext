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

    private String to;

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
