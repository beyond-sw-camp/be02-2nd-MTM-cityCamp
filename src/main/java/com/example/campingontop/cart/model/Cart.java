package com.example.campingontop.cart.model;

import com.example.campingontop.cartHouse.model.CartHouse;
import com.example.campingontop.house.model.House;
import com.example.campingontop.user.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartHouse> cartHouses = new ArrayList<>();


    private Date checkIn;
    private Date checkOut;

    @Column(updatable = false, nullable = false)
    private Date createdAt;

    private Date updatedAt;

    private Integer amount;


    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cart;
    }
}
