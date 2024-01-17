package com.example.campingontop.review.repository.queryDsl;

import com.example.campingontop.house.model.QHouse;
import com.example.campingontop.orderedHouse.model.QOrderedHouse;
import com.example.campingontop.orders.model.QOrders;
import com.example.campingontop.review.model.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;

public class ReviewRepositoryCustomImpl extends QuerydslRepositorySupport implements ReviewRepositoryCustom {
    public ReviewRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Review.class);
        this.queryFactory = queryFactory;
    }

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean hasPaymentHistoryForHouse(Long userId, Long houseId) {
        QOrders orders = QOrders.orders;
        QOrderedHouse orderedHouse = QOrderedHouse.orderedHouse;
        QHouse house = QHouse.house;

        long count = queryFactory
                .selectFrom(orders)
                .innerJoin(orders.orderedHouseList, orderedHouse)
                .innerJoin(orderedHouse.house, house)
                .where(
                        orders.cart.user.id.eq(userId),
                        house.id.eq(houseId)
                )
                .fetchCount();

        return count > 0;
    }
}
