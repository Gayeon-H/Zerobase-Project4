package com.zerobase.reservation.review.repository;

import com.zerobase.reservation.review.entity.Review;
import com.zerobase.reservation.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByStore(Store store);

    Page<Review> findByStore(Store store, Pageable limit);

}
