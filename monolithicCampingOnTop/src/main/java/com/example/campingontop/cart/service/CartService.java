package com.example.campingontop.cart.service;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.cart.model.dto.request.PutDeleteCartDtoReq;
import com.example.campingontop.cart.model.dto.response.GetCartDtoRes;
import com.example.campingontop.cart.model.dto.response.PostCreateCartDtoRes;
import com.example.campingontop.cart.repository.CartRepository;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.CartException;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.houseImage.model.HouseImage;
import com.example.campingontop.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public PostCreateCartDtoRes addToCart(User user, Long houseId, LocalDate checkIn, LocalDate checkOut) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 숙소가 존재하지 않습니다: " + houseId));

        if (isHouseAlreadyInCart(user.getId(), houseId, checkIn, checkOut)) {
            throw new CartException(ErrorCode.DUPLICATED_RESERVATION);
        }

        int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        int totalPrice = house.getPrice() * nights;

        Date fromDate = Date.from(checkIn.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(checkOut.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Cart cart = Cart.builder()
                .user(user)
                .house(house)
                .checkIn(fromDate)
                .checkOut(toDate)
                .price(totalPrice)
                .status(true)
                .build();

        cart = cartRepository.save(cart);

        List<HouseImage> houseImageList = house.getHouseImageList();

        List<String> filenames = new ArrayList<>();
        for (HouseImage productImage : houseImageList) {
            String filename = productImage.getFilename();
            filenames.add(filename);
        }

        PostCreateCartDtoRes res = PostCreateCartDtoRes.toDto(cart, filenames);

        return res;
    }

    private boolean isHouseAlreadyInCart(Long userId, Long houseId, LocalDate checkIn, LocalDate checkOut) {
        Date fromDate = Date.from(checkIn.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(checkOut.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Optional<Cart> existingCart = cartRepository.findByUser_IdAndHouse_IdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                userId, houseId, toDate, fromDate);

        return existingCart.isPresent();
    }


    public List<GetCartDtoRes> getCartsByUserId(Long userId) {
        List<Cart> result = cartRepository.findByUserId(userId);
        List<GetCartDtoRes> getCartDtoResList = new ArrayList<>();

        if (!result.isEmpty()) {
            for (Cart cart : result) {
                List<HouseImage> houseImageList = cart.getHouse().getHouseImageList();

                List<String> filenames = new ArrayList<>();
                for (HouseImage houseImage : houseImageList) {
                    String filename = houseImage.getFilename();
                    filenames.add(filename);
                }

                GetFindHouseDtoRes getFindHouseDtoRes = GetFindHouseDtoRes.toDto(cart.getHouse(), filenames);

                GetCartDtoRes res = GetCartDtoRes.toDto(cart, getFindHouseDtoRes);
                getCartDtoResList.add(res);
            }
            return getCartDtoResList;
        }
        throw new CartException(ErrorCode.CART_EMPTY);
    }

    public void deleteCart(PutDeleteCartDtoReq req) {
        Cart cart = cartRepository.findByUserIdAndCartId(req.getUserId(), req.getCartId());
        if (cart != null) {
            cart.setStatus(false);
            cartRepository.save(cart);
        }
    }


}
