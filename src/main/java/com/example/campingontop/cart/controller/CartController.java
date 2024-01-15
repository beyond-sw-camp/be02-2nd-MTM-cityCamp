package com.example.campingontop.cart.controller;

import com.example.campingontop.cart.service.CartService;
import com.example.campingontop.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name="Cart", description = "Cart 숙소 장바구니 CRUD")
@Api(tags = "Cart")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @Operation(summary = "Cart 숙소 장바구니 등록",
            description = "숙소 장바구니에 생성하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류")})
    @PostMapping("/create/{houseId}")
    public ResponseEntity<String> createCart(@AuthenticationPrincipal User user, @PathVariable Long houseId) {
        cartService.createCart(user, houseId);

        return ResponseEntity.ok().body("house cart create success");
    }


}
