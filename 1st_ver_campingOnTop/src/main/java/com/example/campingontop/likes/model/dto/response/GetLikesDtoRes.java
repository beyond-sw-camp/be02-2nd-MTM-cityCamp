package com.example.campingontop.likes.model.dto.response;

import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetLikesDtoRes {
    private Long id;
    private String email;
    private String name;
    private List<GetFindHouseDtoRes> houseDtoResList;
}
