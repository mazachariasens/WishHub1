package com.example.wishhubproto.model;

import com.example.wishhubproto.model.User;
import com.example.wishhubproto.model.Lists;
import java.awt.image.BufferedImage;
import java.util.List;

public class Wish{

    private int wishID;
    private String wishName;
    private String description;
    private String imgDataPath;


    public Wish(int wishID, String wishName, String description, String imgdatapath) {
        this.wishID = wishID;
        this.wishName = wishName;
        this.description = description;
        this.imgDataPath = imgdatapath;
    }

    public int getWishID() {
        return wishID;
    }

    public String getTitle() {
        return wishName;
    }

    public String getDescription() {
        return description;
    }

    public String getImgDataPath() {
        return imgDataPath;
    }

    public void setWishID(int wishID) {
        this.wishID = wishID;
    }
    public void setTitle(String title) {
        this.wishName = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImgDataPath(String imgDataPath) {
        this.imgDataPath = imgDataPath;
    }




}
