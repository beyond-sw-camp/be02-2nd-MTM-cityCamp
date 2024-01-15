package com.example.campingontop.likes.repository;

import com.example.campingontop.likes.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByUserId(Long userId);
}
