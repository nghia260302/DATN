package com.tuannghia.andshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "clothe")
public class Clothe extends AbstractBase {

    @Column(name = "title")
    private String title;

    @Column(name = "brand")
    private String brand;

    @Column(name = "material")
    private String material;

    @Column(name = "published_date")
    private Date publishedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "original_price")
    private Double originalPrice;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "elasticity_level")
    private Integer elasticityLevel;

    @Lob
    @Column(nullable = false, length = 1000)
    private String description;

    @Column(name = "cover_image")
    private String coverImage;


    @JsonIgnore
    @ManyToMany(mappedBy = "favoriteClothes", fetch = FetchType.EAGER)
    private Set<User> usersWhoFavorited;

    public void addUser(User user) {
        Set<User> users = this.getUsersWhoFavorited();
        if (usersWhoFavorited == null) {
            usersWhoFavorited = new HashSet<>();
        }
        usersWhoFavorited.add(user);
        this.setUsersWhoFavorited(users);
    }

    public void removeUserWhoFavorited(User user) {
        if (this.usersWhoFavorited != null) {
            this.usersWhoFavorited.remove(user);
        }
    }
}