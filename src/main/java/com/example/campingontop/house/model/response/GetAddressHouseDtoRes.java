package com.example.campingontop.house.model.response;

import com.example.campingontop.house.model.House;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAddressHouseDtoRes {
    private Long id;
    private String name;
    private String content;
    private Integer price;

    private String address;
    private Double latitude;
    private Double longitude;
    private Double distance;

    private Integer maxUser;

    private Boolean hasAirConditioner;
    private Boolean hasWashingMachine;
    private Boolean hasBed;
    private Boolean hasHeater;
    private List<String> filenames;

    public static GetAddressHouseDtoRes toDto(House house, List<String> filenames, Double distance) {
        return GetAddressHouseDtoRes.builder()
                .id(house.getId())
                .name(house.getName())
                .content(house.getContent())
                .price(house.getPrice())
                .address(house.getAddress())
                .latitude(house.getLatitude())
                .longitude(house.getLongitude())
                .distance(distance)
                .maxUser(house.getMaxUser())
                .hasAirConditioner(house.getHasAirConditioner())
                .hasWashingMachine(house.getHasWashingMachine())
                .hasBed(house.getHasBed())
                .hasHeater(house.getHasHeater())
                .filenames(filenames)
                .build();
    }

    public static GetAddressHouseDtoRes toDto(House house, Double distance) {
        return GetAddressHouseDtoRes.builder()
                .id(house.getId())
                .name(house.getName())
                .content(house.getContent())
                .price(house.getPrice())
                .address(house.getAddress())
                .latitude(house.getLatitude())
                .longitude(house.getLongitude())
                .distance(distance)
                .maxUser(house.getMaxUser())
                .hasAirConditioner(house.getHasAirConditioner())
                .hasWashingMachine(house.getHasWashingMachine())
                .hasBed(house.getHasBed())
                .hasHeater(house.getHasHeater())
                .build();
    }
}
