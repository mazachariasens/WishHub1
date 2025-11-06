package dk.datamatiker.wishhub.model;

import java.util.ArrayList;
import java.util.List;

public class WishList {
    private int id;
    private String title;

    private String description;

    private int userId;


    private List<Wish> wishes = new ArrayList<>();

    public WishList() {}

    public WishList(String title, String description, int userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }


    // Getters og setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Wish> getWishes() { return wishes; }
    public void setWishes(List<Wish> wishes) { this.wishes = wishes; }
}
