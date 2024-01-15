package com.example.campingontop.likes.controller;

import com.example.campingontop.likes.model.dto.request.PostCreateLikesDtoReq;
import com.example.campingontop.likes.service.LikesService;
import com.example.campingontop.user.model.response.GetUserWithLikeDtoRes;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name="Like", description = "Like 숙소 좋아요 CRUD")
@Api(tags = "Like")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/like")
public class LikesController {
    private final LikesService likesService;

    @Operation(summary = "Like 숙소 좋아요 생성",
            description = "숙소 좋아요 목록을 생성하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/create")
    public ResponseEntity<String> createLikes(@Valid @RequestBody PostCreateLikesDtoReq request) {
        likesService.createLike(request);
        return ResponseEntity.ok().body("house like create success");
    }

    @Operation(summary = "Like 숙소 좋아요 + 유저 정보 조회",
            description = "유저 ID로 유저, 유저가 좋아요한 숙소 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @GetMapping("/{userId}")
    public ResponseEntity<List<GetUserWithLikeDtoRes>> findLikeByUserId(@Valid @PathVariable Long userId) {
        List<GetUserWithLikeDtoRes> likedHouses = likesService.findLikeByUserId(userId);
        return ResponseEntity.ok().body(likedHouses);
    }
}
