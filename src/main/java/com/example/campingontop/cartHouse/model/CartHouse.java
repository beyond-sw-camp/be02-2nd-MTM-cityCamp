package com.example.campingontop.cartHouse.model;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.house.model.House;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "House_id")
    private House house;

    public static CartHouse createCartHouse(Cart cart, House house) {
        CartHouse cartHouse = new CartHouse();
        cartHouse.setCart(cart);
        cartHouse.setHouse(house);
        return cartHouse;
    }
}
