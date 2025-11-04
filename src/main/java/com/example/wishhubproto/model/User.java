package com.example.wishhubproto.model;

import java.sql.Timestamp;

public class User {
    int userID;
    private String userName;
    private String passwordHash;
    private String email;
    private Timestamp createdAtTimeOf;

    //The purpose of writing a blank constructor, is, e.g. when I use it in the login get controller method, to create
    //an instance of the User object, where I wish to have this instance populated later, rather than imidiatly at the
    //creation of its instance.
    public User() {
    }

    public User(int userID, String userName, String passwordHash, String email, Timestamp createdAtTimeOf) {
        this.userID = userID;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.email = email;
        this.createdAtTimeOf = createdAtTimeOf;
    }

    public int getUserID() {
        return userID;
    }
    public String getUserName() {
        return userName;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getEmail() {
        return email;
    }
    public Timestamp getCreatedAtTimeOf() {
        return createdAtTimeOf;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setCreatedAtTimeOf(Timestamp createdAtTimeOf) {
        this.createdAtTimeOf = createdAtTimeOf;
    }
}


