package dk.datamatiker.wishhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "items")  // matcher DB-tabellen
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title; // matcher items.title

    private String description;

    private String link;

    private Integer quantity = 1;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id")
    private WishList wishList;

    public Wish() {}

    public Wish(String title, String description, String link, Integer quantity, String imageUrl, WishList wishList) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.wishList = wishList;
    }

    // Getters og setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public WishList getWishList() { return wishList; }
    public void setWishList(WishList wishList) { this.wishList = wishList; }
}
