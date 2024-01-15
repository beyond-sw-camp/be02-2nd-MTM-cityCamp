package com.example.campingontop.cartHouse.repository;

import com.example.campingontop.cartHouse.model.CartHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartHouseRepository extends JpaRepository<CartHouse, Long> {
    CartHouse findByCartIdAndHouseId(Long cartId, Long houseId);
}
