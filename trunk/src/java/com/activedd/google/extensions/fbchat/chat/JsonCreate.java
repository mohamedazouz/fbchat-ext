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
 * @author Activedd2
 *
 * this class is to load the receiving message to the user
 */
public class JsonCreate {

    /*
     * this function is to create  json file
     *
     * to check it also if the user put more than messsage within two seconds
     * 
     */
    public void createJsonFile(String f, String msg, String to) throws JSONException, FileNotFoundException, IOException {
        Date d = new Date();
        JSONArray jSONArray = null;
        JSONObject jSONObject = new JSONObject();
        //File file = new File("/media/D/Azouz/NetBeansProjects/proxy_facebook_chat/web/recentchat/" + to.subSequence(1, to.lastIndexOf("@")) + ".json");
        ProtectionDomain domain = this.getClass().getProtectionDomain();
        File filea = new File(domain.getCodeSource().getLocation().getPath());
        String path=filea.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getCanonicalFile().getPath();
        File file = new File(path+"/web/chat/"+to.subSequence(1, to.lastIndexOf("@")) + ".json");
        System.out.println("Location: " + domain.getCodeSource().getLocation().getPath() );
        System.out.println("path " +path );
        long nownow = d.getTime();
        d.getTime();
        int now = d.getSeconds();
        System.out.println("now=>" + now);
        d = new Date(file.lastModified());
        int last = d.getSeconds();
        System.out.println("from=>" + last);
        int remain = Math.abs(last - now);
        System.out.println("total=>" + remain);
        String tot = "";
        if (remain < 2 && file.exists()) {
            System.out.println("allah b2aa");
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                tot += sc.nextLine();
            }
            jSONArray = new JSONArray(tot);
            sc.close();
        }
        jSONObject.put("msg", msg);
        jSONObject.put("from", f);
        jSONObject.put("time", nownow);
        if (jSONArray == null) {
            jSONArray = new JSONArray();
        }
        jSONArray.put(jSONObject);
        PrintWriter out = new PrintWriter(file);
        String json = jSONArray.toString(5);
        out.append(json);
        out.close();
        System.out.println(jSONArray);
    }
}
