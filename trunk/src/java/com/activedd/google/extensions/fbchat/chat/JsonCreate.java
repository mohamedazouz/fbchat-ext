/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

    private final int LAST_MODIFICATION_SECODS = 4;
    
    private String realPath;

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
//        ProtectionDomain domain = this.getClass().getProtectionDomain();
        //String path = domain.getCodeSource().getLocation().getPath();
        //String p_realPath = path.substring(path.indexOf("/"), path.indexOf("WEB-INF"));
        File file = new File(getRealPath() + to.subSequence(1, to.lastIndexOf("@")) + ".json");
        long currentTime = currentDate.getTime();
        int currentSeconds = currentDate.getSeconds();
        currentDate = new Date(file.lastModified());
        int last = currentDate.getSeconds();

        int remainingSeconds = Math.abs(last - currentSeconds);
        StringBuilder tot = new StringBuilder("");
        if (remainingSeconds < LAST_MODIFICATION_SECODS && file.exists()) {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                tot.append(sc.nextLine());
            }
            try {
                lastReceivedMessages = new JSONArray(tot.toString());
            } catch (Exception e) {
                lastReceivedMessages=new JSONArray();
            }
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

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    /**
     * @return the realPath
     */
    public String getRealPath() {
        return realPath;
    }
}
