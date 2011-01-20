/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activedd.google.extensions.fbchat.chat;

import java.util.ArrayList;

/**
 *
 * @author Activedd2
 */
public class BuddyList {

    private ArrayList<FriendBuddy> list = new ArrayList<FriendBuddy>();

    /**
     * @return the list
     */
    public ArrayList<FriendBuddy> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList<FriendBuddy> list) {
        this.list = list;
    }
    
}
