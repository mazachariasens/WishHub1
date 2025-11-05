package dk.datamatiker.wishhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishlists")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes = new ArrayList<>();

    public WishList() {}

    public WishList(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    // Getters og setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Wish> getWishes() { return wishes; }
    public void setWishes(List<Wish> wishes) { this.wishes = wishes; }

    public void addWish(Wish wish) {
        wishes.add(wish);
        wish.setWishList(this);
    }

    public void removeWish(Wish wish) {
        wishes.remove(wish);
        wish.setWishList(null);
    }
}
