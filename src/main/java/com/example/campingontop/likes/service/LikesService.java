package com.example.campingontop.likes.service;

import com.example.campingontop.houseImage.model.HouseImage;
import com.example.campingontop.likes.model.Likes;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.likes.model.dto.request.PostCreateLikesDtoReq;
import com.example.campingontop.likes.repository.LikesRepository;
import com.example.campingontop.user.model.User;
import com.example.campingontop.user.model.response.GetUserWithLikeDtoRes;
import com.example.campingontop.user.repository.queryDsl.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public void createLike(PostCreateLikesDtoReq req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다. user_id: " + req.getUserId()));

        House house = houseRepository.findById(req.getHouseId())
                .orElseThrow(() -> new EntityNotFoundException("해당 숙소를 찾을 수 없습니다. house_id: " + req.getHouseId()));

        Likes likes = Likes.builder()
                .user(user)
                .house(house)
                .build();

        likesRepository.save(likes);
    }

    public List<GetUserWithLikeDtoRes> findLikeByUserId(Long userId) {
        List<Likes> likes = likesRepository.findByUserId(userId);
        List<GetUserWithLikeDtoRes> result = new ArrayList<>();

        if (!likes.isEmpty()) {
            for (Likes like : likes) {
                User user = like.getUser();
                House house = like.getHouse();
                List<HouseImage> houseImageList = house.getHouseImageList();

                List<String> filenames = new ArrayList<>();
                for (HouseImage productImage : houseImageList) {
                    String filename = productImage.getFilename();
                    filenames.add(filename);
                }

                GetFindHouseDtoRes houseDto = GetFindHouseDtoRes.toDto(house, filenames);

                GetUserWithLikeDtoRes userDto = GetUserWithLikeDtoRes.builder()
                        .id(like.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .nickName(user.getNickName())
                        .phoneNum(user.getPhoneNum())
                        .gender(user.getGender().name())
                        .birthDay(user.getBirthDay())
                        .likedHouses(Collections.singletonList(houseDto))
                        .build();

                result.add(userDto);
            }
            return result;
        }
        return null;
    }
}
