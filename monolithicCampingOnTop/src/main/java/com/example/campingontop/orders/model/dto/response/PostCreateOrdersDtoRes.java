package com.example.campingontop.orders.model.dto.response;

import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class PostCreateOrdersDtoRes {
    private Long id;
    private String username;
    private String email;
    private Date checkIn;
    private Date checkOut;
    private Integer price;
    private List<GetFindHouseDtoRes> houseList;
}
