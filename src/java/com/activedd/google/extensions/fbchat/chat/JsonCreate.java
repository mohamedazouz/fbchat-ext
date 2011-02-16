/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.ProtectionDomain;
import java.util.Date;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *
 * @author Activedd2
 *
 * creating json file for each user contains its receiving messages
 */
public class JsonCreate {

    private final int LAST_MODIFICATION_SECODS = 2;

    /**
     * Store receiving messages in 1 json file and append to file if its last modification within 2 seconds
     *
     * @param f message sender
     * @param msg message text
     * @param to message receiver
     * @throws JSONException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void createJsonFile(String f, String msg, String to) throws JSONException, FileNotFoundException, IOException {

        Date currentDate = new Date();
        JSONArray lastReceivedMessages = null;
        JSONObject receivedMessage = new JSONObject();
        ProtectionDomain domain = this.getClass().getProtectionDomain();
        /*File filea = new File(domain.getCodeSource().getLocation().getPath());
        String path = filea.getParentFile().getParentFile().getPath();*/
        String path = domain.getCodeSource().getLocation().getPath();
        String p_realPath = path.substring(path.indexOf("/"), path.indexOf("WEB-INF"));
        File file = new File(p_realPath + "chat/" + to.subSequence(1, to.lastIndexOf("@")) + ".json");
        long currentTime = currentDate.getTime();
        int currentSeconds = currentDate.getSeconds();
        currentDate = new Date(file.lastModified());
        int last = currentDate.getSeconds();
        
        int remainingSeconds = Math.abs(last - currentSeconds);
        String tot = "";
        if (remainingSeconds < LAST_MODIFICATION_SECODS && file.exists()) {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                tot += sc.nextLine();
            }
            lastReceivedMessages = new JSONArray(tot);
            sc.close();
        }
        receivedMessage.put("msg", msg);
        receivedMessage.put("from", f);
        receivedMessage.put("time", currentTime);
        if (lastReceivedMessages == null) {
            lastReceivedMessages = new JSONArray();
        }
        lastReceivedMessages.put(receivedMessage);
        PrintWriter out = new PrintWriter(file);
        String json = lastReceivedMessages.toString(5);
        out.append(json);
        out.close();
    }
}
