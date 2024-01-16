package com.example.campingontop.house.model.request;

import lombok.*;

import javax.persistence.Entity;

@Data
@Builder
public class GetUserLocationDtoReq {
    private double latitude;
    private double longitude;
    //단위는 메다- (m)
    private double distance;
}
