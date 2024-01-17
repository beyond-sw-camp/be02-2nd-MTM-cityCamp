package com.example.campingontop.review.repository.queryDsl;

public interface ReviewRepositoryCustom {
    boolean hasPaymentHistoryForHouse(Long userId, Long houseId);
}
