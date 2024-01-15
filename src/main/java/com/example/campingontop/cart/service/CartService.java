package com.example.campingontop.cart.service;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.cart.repository.CartRepository;
import com.example.campingontop.cartHouse.model.CartHouse;
import com.example.campingontop.cartHouse.repository.CartHouseRepository;
import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.HouseException;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.user.model.User;
import com.example.campingontop.user.repository.queryDsl.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final CartHouseRepository cartHouseRepository;

    public void createCart(User user, Long houseId) {
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        Optional<House> result = houseRepository.findById(houseId);
        if (result.isEmpty()) {
            throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
        }

        House house = result.get();
        CartHouse cartHouse = cartHouseRepository.findByCartIdAndHouseId(cart.getId(), houseId);

        if (cartHouse == null) {
            cartHouse = CartHouse.createCartHouse(cart, house);
            cartHouse = cartHouseRepository.save(cartHouse);

        }
    }


}
