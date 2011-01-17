/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Activedd2
 */
public class CreatJsonFile {

    public void createJsonFile(String f, String t, String d) throws JSONException, FileNotFoundException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("from", f);
        jSONObject.append("to", t);
        jSONObject.append("des", d);
        PrintWriter out = new PrintWriter("E:\\chat\\out.json");
        String json = jSONObject.toString(5);
        out.append(json);
        out.close();
    }
}
