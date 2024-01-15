package com.example.campingontop.cart.service;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.cart.model.dto.PostCreateCartDtoReq;
import com.example.campingontop.cart.repository.CartRepository;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.user.model.User;
import com.example.campingontop.user.repository.queryDsl.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public void createLike(PostCreateCartDtoReq req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다. user_id: " + req.getUserId()));

        House house = houseRepository.findById(req.getHouseId())
                .orElseThrow(() -> new EntityNotFoundException("해당 숙소를 찾을 수 없습니다. house_id: " + req.getHouseId()));

        Cart cart= Cart.builder()
                .user(user)
                .house(house)
                .build();

        cart = cartRepository.save(cart);
    }


}
