package com.example.campingontop.cartHouse.service;

import com.example.campingontop.cartHouse.repository.CartHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHouseService {
    private final CartHouseRepository cartHouseRepository;


}
