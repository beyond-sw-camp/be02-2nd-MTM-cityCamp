package com.example.campingontop.orders.model;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.orderedHouse.model.OrderedHouse;
import com.example.campingontop.review.model.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cart_id")
    private Cart cart;

    private String impUid;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<OrderedHouse> orderedHouseList = new ArrayList<>();

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<Review> reviewList = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.orderedHouseList == null) {
            this.orderedHouseList = new ArrayList<>();
        }
    }
}
