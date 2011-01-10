/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JfacebookChat;

import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import net.sf.jfacebookiml.FacebookAdapter;
import net.sf.jfacebookiml.FacebookBuddyList;
import net.sf.jfacebookiml.FacebookHttpClient;
import net.sf.jfacebookiml.FacebookMessage;
import net.sf.jfacebookiml.FacebookUser;

/**
 *
 * @author Activedd2
 */
public class Chat {

    static final Properties log4jProperties = new Properties();
    private static final String GOOD_EMAIL = "m_aliazouz@yahoo.com";
    private static final String GOOD_PASSWORD = "7117340";

    static {
        log4jProperties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        log4jProperties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
        log4jProperties.setProperty("log4j.rootLogger", "TRACE, stdout");

        PropertyConfigurator.configure(log4jProperties);
    }

    void connect() {
        FacebookAdapter adapter = new FacebookAdapter();
        adapter.initialize(GOOD_EMAIL, GOOD_PASSWORD);

        FacebookHttpClient client = new FacebookHttpClient(adapter);
        
        FacebookBuddyList buddyList = new FacebookBuddyList(adapter);

        for (int i = 0; i < 10; i++) {
            System.out.println("==============================");
        
            System.out.println("==============================");
        }
        
        System.out.println("==============================");
        FacebookUser user = buddyList.getBuddyFromCacheByID(adapter.getUID());
           System.out.println(user.toString());


        System.out.println("==============================");
        //System.out.println(user.uid);
        System.out.println("==============================");
        adapter.Logout();
        /*for(int i=0;i<100;i++){
        System.out.println(buddyList.getBuddyFromCacheByID(i));
        }*/
        //FacebookUser user=new FacebookUser(adapter.getUID(),adapter.get));
    }
}
