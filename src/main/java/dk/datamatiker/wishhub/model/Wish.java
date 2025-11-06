package dk.datamatiker.wishhub.model;


public class Wish {

    private int id;

    private String title; // matcher items.title

    private String description;

    private Integer quantity = 1;

    private int wishlistId;

    public Wish() {}

    public Wish(String title, String description, Integer quantity, int wishlistId) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.wishlistId = wishlistId;
    }

    // Getters og setters
    public int getId() { return id; }
    public void setId(int id){
        this.id = id;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public int getWishlistId() { return wishlistId; }

    public void setWishlistId(int wishlistId) { this.wishlistId = wishlistId; }
}
