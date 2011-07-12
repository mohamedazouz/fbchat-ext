/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import com.google.code.facebookapi.FacebookJsonRestClient;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 *
 * @author ibrahim
 */
public class ServerConfiguration {

    private XMPPConnection connection; // Connect to facebook chat and also it implement all xmpp chat for fcebook
    private final SecurityMode securityMode = SecurityMode.enabled;
    private final boolean isSaslAuthenticationEnabled = true;
    public final boolean isCompressionEnabled = false;
    private final boolean isReconnectionAllowed = false;
    private FacebookJsonRestClient facebook; // facebook client to get sessionkey and enable me to acces friends details like a photos and status
    private MessageListenerImp messageListenerImp;
    private ConnectionConfiguration config = null;

    ServerConfiguration(String domain, int port) throws XMPPException {
        SASLAuthentication.registerSASLMechanism("X-FACEBOOK-PLATFORM",
                FacebookConnectSASLMechanism.class);
        SASLAuthentication.supportSASLMechanism("X-FACEBOOK-PLATFORM", 0);
        config = new ConnectionConfiguration(domain, port);
        config.setSecurityMode(securityMode);
        config.setSASLAuthenticationEnabled(isSaslAuthenticationEnabled);
        config.setCompressionEnabled(isCompressionEnabled);
        config.setReconnectionAllowed(isReconnectionAllowed);
    }

    public ConnectionConfiguration getConfiguration() {
        return config;
    }
     public XMPPConnection getConnection() {
        return connection;
    }
}
