package com.example.wishhubproto.model;


public class Lists {

    String listName;
    int listID;
    int userID;

    public Lists(String listName, int listID, int userID) {
        this.listID = listID;
        this.listName = listName;
        this.userID = userID;
    }

    public String getListName() {
        return listName;
    }
    public int getListID() {
        return listID;
    }
    public void setListName(String listName) {
        this.listName = listName;
    }
    public void setListID(int listID) {
        this.listID = listID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }


}
