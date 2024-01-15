package com.example.campingontop.cart.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateCartDtoReq {
    private Long userId;
    private Long houseId;
}
