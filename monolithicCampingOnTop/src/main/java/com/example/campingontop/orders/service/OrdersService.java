package com.example.campingontop.orders.service;

import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.orderedHouse.model.OrderedHouse;
import com.example.campingontop.orderedHouse.repository.OrderedHouseRepository;
import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.orders.model.dto.request.PostCreateOrdersDtoReq;
import com.example.campingontop.orders.model.dto.response.PostCreateOrdersDtoRes;
import com.example.campingontop.orders.repository.OrdersRepository;
import com.example.campingontop.user.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final IamportClient iamportClient;
    private final OrdersRepository ordersRepository;
    private final OrderedHouseRepository orderedHouseRepository;

    @Transactional
    public Boolean paymentValidation(PostCreateOrdersDtoReq req) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = getPaymentInfo(req.getImpUid());
        Integer amount = response.getResponse().getAmount().intValue();

        Integer totalPrice = req.getCart().getPrice();

        if(amount.equals(totalPrice) ) {
            createOrders(req);
            return true;
        }
        return false;
    }


    // 정보 가져오는 메소드로 사용하고 검증하는 메소드를 따로 만들자.
    public IamportResponse getPaymentInfo(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = iamportClient.paymentByImpUid(impUid);

        return response;
    }

    public PostCreateOrdersDtoRes createOrders(PostCreateOrdersDtoReq req) throws IamportResponseException, IOException {
        Orders orders = Orders.builder()
                .cart(req.getCart())
                .impUid(req.getImpUid())
                .build();
        orders = ordersRepository.save(orders);

        IamportResponse<Payment> response = getPaymentInfo(orders.getImpUid());
        String customData = response.getResponse().getCustomData();

        Gson gson = new Gson();
        Type houseListType = new TypeToken<List<GetFindHouseDtoRes>>(){}.getType();
        List<GetFindHouseDtoRes> houseDtoList = gson.fromJson(customData, houseListType);

        for (GetFindHouseDtoRes res : houseDtoList) {
            House house = House.builder()
                    .id(res.getId())
                    .name(res.getName())
                    .price(res.getPrice())
                    .build();

            OrderedHouse orderedHouse = OrderedHouse.builder()
                    .orders(orders)
                    .house(house)
                    .build();
            orders.getOrderedHouseList().add(orderedHouse);

            orderedHouseRepository.save(orderedHouse);
        }

        PostCreateOrdersDtoRes dto = PostCreateOrdersDtoRes.builder()
                .id(orders.getId())
                .username(orders.getCart().getUser().getUsername())
                .email(orders.getCart().getUser().getEmail())
                .checkIn(orders.getCart().getCheckIn())
                .checkOut(orders.getCart().getCheckOut())
                .price(orders.getCart().getPrice())
                .houseList(houseDtoList)
                .build();

        return dto;
    }

    public List<PostCreateOrdersDtoRes> findOrdersList(User user) throws IamportResponseException, IOException {
        List<Orders> result = ordersRepository.findAllByCart_User(user);
        List<PostCreateOrdersDtoRes> dtoList = new ArrayList<>();

        if (!result.isEmpty()) {
            for (Orders orders : result) {
                IamportResponse<Payment> response = getPaymentInfo(orders.getImpUid());
                String customData = response.getResponse().getCustomData();

                Gson gson = new Gson();
                Type productListType = new TypeToken<List<GetFindHouseDtoRes>>(){}.getType();
                List<GetFindHouseDtoRes> houseList = gson.fromJson(customData, productListType);

                List<GetFindHouseDtoRes> houseInfoList = new ArrayList<>();


                PostCreateOrdersDtoRes res = PostCreateOrdersDtoRes.builder()
                        .id(orders.getId())
                        .username(orders.getCart().getUser().getUsername())
                        .email(orders.getCart().getUser().getEmail())
                        .checkIn(orders.getCart().getCheckIn())
                        .checkOut(orders.getCart().getCheckOut())
                        .price(orders.getCart().getPrice())
                        .houseList(houseInfoList)
                        .build();

                dtoList.add(res);
            }
            return dtoList;
        }
        return null;
    }
}
