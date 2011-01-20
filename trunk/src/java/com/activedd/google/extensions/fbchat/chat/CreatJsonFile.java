/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Activedd2
 */
public class CreatJsonFile {

    public void createJsonFile(String f, String msg) throws JSONException, FileNotFoundException {
        Date d = new Date();
        JSONArray jSONArray = null;
        JSONObject jSONObject = new JSONObject();
        File file = new File("/media/D/Azouz/chat/"+f+".json");//.subSequence(1, f.lastIndexOf("@"))
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
