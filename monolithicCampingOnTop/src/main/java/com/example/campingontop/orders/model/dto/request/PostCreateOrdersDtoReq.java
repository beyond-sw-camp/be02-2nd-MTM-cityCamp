package com.example.campingontop.orders.model.dto.request;

import com.example.campingontop.cart.model.Cart;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostCreateOrdersDtoReq {
    private Cart cart;

    private String impUid;
}
