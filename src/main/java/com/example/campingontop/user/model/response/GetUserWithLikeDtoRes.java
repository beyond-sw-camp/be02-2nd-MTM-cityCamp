package com.example.campingontop.user.model.response;

import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetUserWithLikeDtoRes {
    private Long id;
    private String email;
    private String name;
    private String nickName;
    private String phoneNum;
    private String gender;
    private String birthDay;
    private String img;
    private Date createdAt;
    private Date updatedAt;
    private List<GetFindHouseDtoRes> likedHouses;
}
