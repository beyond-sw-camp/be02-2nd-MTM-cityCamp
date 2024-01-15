package com.example.campingontop.house.repository.queryDsl;

import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.QHouse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HouseRepositoryCustomImpl extends QuerydslRepositorySupport implements HouseRepositoryCustom {
    public HouseRepositoryCustomImpl() {
        super(House.class);
    }
    QHouse house;

    public HouseRepositoryCustomImpl(Class<?> domainClass, JPAQueryFactory jpaQueryFactory) {
        super(domainClass);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<House> findList(Pageable pageable) {
        QHouse house = new QHouse("house");

        List<House> result = from(house)
                .leftJoin(house.houseImageList).fetchJoin()
                .leftJoin(house.user).fetchJoin()
                .where(house.status.eq(true))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }

    @Override
    public Optional<House> findActiveHouse(Long id) {
        QHouse qHouse = new QHouse("house");

        Optional<House> house = Optional.ofNullable(from(qHouse)
                .where(qHouse.id.eq(id).and(qHouse.status.eq(true)))
                .fetchOne());

        return house;
    }

    @Override
    public Page<House> findByPriceDesc(Pageable pageable){
        QHouse house = QHouse.house;

        List<House> houses = from(house)
                .leftJoin(house.houseImageList).fetchJoin()
                .leftJoin(house.user).fetchJoin()
                .orderBy(house.price.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }

    @Override
    public Page<House> findByPriceAsc(Pageable pageable){
        QHouse house = QHouse.house;

        List<House> houses =from(house)
                .leftJoin(house.houseImageList).fetchJoin()
                .leftJoin(house.user).fetchJoin()
                .orderBy(house.price.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }

//
    @Override
    public Page<House> findByName(Pageable pageable, String name){
        QHouse house = QHouse.house;

        List<House> houses = from(house)
                .leftJoin(house.houseImageList).fetchJoin()
                .leftJoin(house.user).fetchJoin()
                .where(house.name.like("%"+name+"%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }

    @Override
    public Page<House> findByAddress(Pageable pageable, String address) {
        QHouse house = QHouse.house;

        List<House> houses = from(house)
                .leftJoin(house.houseImageList).fetchJoin()
                .leftJoin(house.user).fetchJoin()
                .where(house.address.like("%"+address+"%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());
        return new PageImpl<>(houses, pageable, pageable.getPageSize());
    }



    /*
    @Override
    public List<House> findHousesWithinDistance(Double baseLat, Double baseLon) {
        QHouse house = QHouse.house;

        NumberTemplate<Double> distanceExpression = DistanceUtils.calculateDistance(
                house.latitude,
                house.longitude,
                Expressions.constant(baseLat),
                Expressions.constant(baseLon)
        );

        List<House> houses = from(house)
                .where(house.status.eq(true).and(distanceExpression.loe(1.0))) // 고정된 반경 1km
                .select(house)
                .fetch();

        houses.sort(Comparator.comparingDouble(h ->
                DistanceUtils.calculateDistance(baseLat, baseLon, h.getLatitude(), h.getLongitude())));

        return houses;
    }


    private double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return DistanceUtils.EARTH_RADIUS * c;
    }
    */
}
