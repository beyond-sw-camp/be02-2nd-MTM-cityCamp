package com.example.campingontop.house.service;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.HouseException;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.request.GetUserLocationDtoReq;
import com.example.campingontop.house.model.request.GetHouseListPagingDtoReq;
import com.example.campingontop.house.model.request.PostCreateHouseDtoReq;
import com.example.campingontop.house.model.request.PutUpdateHouseDtoReq;
import com.example.campingontop.house.model.response.*;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.houseImage.model.HouseImage;
import com.example.campingontop.houseImage.service.HouseImageService;
import com.example.campingontop.user.model.User;
import com.example.campingontop.utils.DistanceCalculateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.campingontop.utils.DistanceCalculateUtils.EARTH_RADIUS;


@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService {
    private final HouseRepository houseRepository;
    private final HouseImageService houseImageService;

    public List<GetFindHouseDtoRes> findByPriceDesc(GetHouseListPagingDtoReq req){
        Pageable pageable = PageRequest.of(req.getPage()-1, req.getSize());
        Page<House> result = houseRepository.findByPriceDesc(pageable);
        List<GetFindHouseDtoRes> houseList = new ArrayList<>();
        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();
            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }
            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }
    public List<GetFindHouseDtoRes> findByPriceAsc(GetHouseListPagingDtoReq req){
        Pageable pageable = PageRequest.of(req.getPage()-1, req.getSize());
        Page<House> result = houseRepository.findByPriceAsc(pageable);
        List<GetFindHouseDtoRes> houseList = new ArrayList<>();
        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();
            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }
            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }
    public List<GetFindHouseDtoRes> findByName(GetHouseListPagingDtoReq req, String name){
        Pageable pageable = PageRequest.of(req.getPage()-1, req.getSize());
        Page<House> result = houseRepository.findByName(pageable, name);
        List<GetFindHouseDtoRes> houseList = new ArrayList<>();
        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();
            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }
            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }
    public List<GetAddressHouseDtoRes> findaroundHouseList(GetUserLocationDtoReq input) {
        //현재 위도 좌표 (y 좌표)
        double nowLatitude = input.getLatitude();
        //현재 경도 좌표 (x 좌표)
        double nowLongitude = input.getLongitude();

        //m당 y 좌표 이동 값
        double mForLatitude = (1 / (EARTH_RADIUS * 1 * (Math.PI / 180))) / 1000;
        //m당 x 좌표 이동 값
        double mForLongitude = (1 / (EARTH_RADIUS * 1 * (Math.PI / 180) * Math.cos(Math.toRadians(nowLatitude)))) / 1000;

        //현재 위치 기준 검색 거리 좌표
        double maxY = nowLatitude + (input.getDistance() * mForLatitude);
        double minY = nowLatitude - (input.getDistance() * mForLatitude);
        double maxX = nowLongitude + (input.getDistance() * mForLongitude);
        double minX = nowLongitude - (input.getDistance() * mForLongitude);

        //해당되는 좌표의 범위 안에 있는 가맹점
        List<House> tempAroundHouseList = houseRepository.getAroundHouseList(maxY, maxX, minY, minX);
        List<GetAddressHouseDtoRes> resultAroundHouseList = new ArrayList<>();

        //정확한 거리 측정
        for (House aroundHouse : tempAroundHouseList) {
            double distance = DistanceCalculateUtils.getDistance(nowLatitude, nowLongitude, aroundHouse.getLatitude(), aroundHouse.getLongitude());
            if (distance < input.getDistance()) {
                List<HouseImage> houseImageList = aroundHouse.getHouseImageList();
                List<String> filenames = new ArrayList<>();
                for (HouseImage productImage : houseImageList) {
                    String filename = productImage.getFilename();
                    filenames.add(filename);
                }
                GetAddressHouseDtoRes res = GetAddressHouseDtoRes.toDto(aroundHouse, filenames, distance);
                resultAroundHouseList.add(res);
                resultAroundHouseList = resultAroundHouseList.stream().sorted(Comparator.comparing(GetAddressHouseDtoRes::getDistance)).collect(Collectors.toList());
            }
        }
        return resultAroundHouseList;
    }

    public List<GetFindHouseDtoRes> findByAddress(GetHouseListPagingDtoReq req, String address){
        Pageable pageable = PageRequest.of(req.getPage()-1, req.getSize());
        Page<House> result = houseRepository.findByAddress(pageable, address);
        List<GetFindHouseDtoRes> houseList = new ArrayList<>();
        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();
            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }
            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }

    public PostCreateHouseDtoRes createHouse(User user, PostCreateHouseDtoReq request, MultipartFile[] uploadFiles) {
        Optional<House> result = houseRepository.findByName(request.getName());
        if (result.isPresent()) {
            throw new HouseException(ErrorCode.DUPLICATED_HOUSE, String.format("숙소 이름: %s", request.getName()));
        }

        House house = House.builder()
                .name(request.getName())
                .content(request.getContent())
                .price(request.getPrice())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .maxUser(request.getMaxUser())
                .hasAirConditioner(request.getHasAirConditioner())
                .hasWashingMachine(request.getHasWashingMachine())
                .hasBed(request.getHasBed())
                .hasHeater(request.getHasHeater())
                .user(user)
                .status(true)
                .likeCnt(0)
                .build();

        house = houseRepository.save(house);

        houseImageService.createHouseImage(house.getId(), uploadFiles);

        PostCreateHouseDtoRes response = PostCreateHouseDtoRes.toDto(house);

        return response;
    }

    @Transactional(readOnly = true)
    public GetFindHouseDtoRes findHouseById(Long houseId) {
        Optional<House> result = houseRepository.findActiveHouse(houseId);
        if (result.isPresent()) {
            House house = result.get();
            List<HouseImage> houseImageList = house.getHouseImageList();

            List<String> filenames = new ArrayList<>();
            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            return res;
        }
        throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
    }

    @Transactional(readOnly = true)
    public List<GetFindHouseDtoRes> findHouseList(GetHouseListPagingDtoReq req) {
        Pageable pageable = PageRequest.of(req.getPage()-1, req.getSize());
        Page<House> result = houseRepository.findList(pageable);

        List<GetFindHouseDtoRes> houseList = new ArrayList<>();

        for (House house : result.getContent()) {
            List<HouseImage> houseImageList = house.getHouseImageList();

            List<String> filenames = new ArrayList<>();
            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }

    /*
    @Transactional(readOnly = true)
    public List<House> findHousesWithinDistance(Double latitude, Double longitude) {
        return houseRepository.findHousesWithinDistance(latitude, longitude);
    }
    */

    public GetHouseLikeDtoRes addHeartHouse(User user, Long id) {
        Optional<House> result = houseRepository.findById(id);

        if (result.isPresent()) {
            House house = result.get();
            house.increaseLikeCount();
            house = houseRepository.save(house);

            GetHouseLikeDtoRes res = GetHouseLikeDtoRes.toDto(house);
            return res;
        }
        throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
    }

    public PutUpdateHouseDtoRes updateHouse(User user, PutUpdateHouseDtoReq req, Long houseId) {
        Optional<House> result = houseRepository.findById(houseId);
        if (result.isPresent()) {
            House house = result.get();

            house.setName(req.getName());
            house.setContent(req.getContent());
            house.setPrice(req.getPrice());
            house.setAddress(req.getAddress());
            house.setLatitude(req.getLatitude());
            house.setLongitude(req.getLongitude());
            house.setMaxUser(req.getMaxUser());
            house.setStatus(req.getIsActive());
            house.setHasAirConditioner(req.getHasAirConditioner());
            house.setHasWashingMachine(req.getHasWashingMachine());
            house.setHasBed(req.getHasBed());
            house.setHasHeater(req.getHasHeater());

            house = houseRepository.save(house);

            PutUpdateHouseDtoRes res = PutUpdateHouseDtoRes.toDto(house);
            return res;
        }
        throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
    }

    public void deleteHouse(User user, Long houseId) {
        Optional<House> result = houseRepository.findActiveHouse(houseId);
        if (result.isPresent()) {
            House house = result.get();
            house.setStatus(false);
            houseRepository.save(house);
            return;
        }
        throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
    }
}
