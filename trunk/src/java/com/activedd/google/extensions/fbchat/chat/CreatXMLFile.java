/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class CreatXMLFile {
    /*
     * create xml file which contain all the chat history between to user
     */
    public void createXML(String f, String t, String d) throws Exception {
        String str = "1";
        int no = Integer.parseInt(str);
        Element rootElement;
        

        File file = new File("E:\\chat\\out.xml");
        file.getAbsolutePath();
        Document document;
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder =
                documentBuilderFactory.newDocumentBuilder();
        if (file.exists()) {
            document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            rootElement = document.getDocumentElement();
        } else {
            document = documentBuilder.newDocument();
            rootElement = document.createElement("chat");
            document.appendChild(rootElement);
        }


        for (int i = 1; i <= no; i++) {
            String elementmessage = "message";
            String elementfrom = "from";
            String elementto = "to";
            String elementdes = "des";
            Element message = document.createElement(elementmessage);
            Element from = document.createElement(elementfrom);
            from.appendChild(document.createTextNode(f));
            Element to = document.createElement(elementto);
            to.appendChild(document.createTextNode(t));
            Element des = document.createElement(elementdes);
            des.appendChild(document.createTextNode(d));
            message.appendChild(from);
            message.appendChild(to);
            message.appendChild(des);
            rootElement.appendChild(message);
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }
}
