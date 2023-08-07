package com.zerobase.reservation.review.service;

import com.zerobase.reservation.common.exception.BizException;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.repository.ReservationRepository;
import com.zerobase.reservation.reservation.type.ReservationState;
import com.zerobase.reservation.review.entity.Review;
import com.zerobase.reservation.review.model.ReviewInput;
import com.zerobase.reservation.review.model.ReviewResult;
import com.zerobase.reservation.review.repository.ReviewRepository;
import com.zerobase.reservation.store.entity.Store;
import com.zerobase.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    @Override
    public ServiceResult addReview(Long id, String email, ReviewInput reviewInput) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (!optionalReservation.isPresent()) {
            return ServiceResult.fail("예약이 존재하지 않습니다.");
        }
        Reservation reservation = optionalReservation.get();

        Optional<Store> optionalStore = storeRepository.findById(reservation.getStore().getId());
        if (!optionalStore.isPresent()) {
            return ServiceResult.fail("매장이 존재하지 않습니다.");
        }
        Store store = optionalStore.get();

        if (!reservation.getReservationMember().getEmail().equals(email)) {
            return ServiceResult.fail("예약했던 회원만 작성할 수 있습니다.");
        }

        if (!reservation.getReservationState().equals(ReservationState.CHECK_VISIT)) {
            return ServiceResult.fail("방문 완료한 예약만 작성할 수 있습니다.");
        }

        reviewRepository.save(Review.builder()
                .store(reservation.getStore())
                .Member(reservation.getReservationMember())
                .starRating(reviewInput.getStarRating())
                .contents(reviewInput.getContents())
                .regDate(LocalDateTime.now())
                .build());

        updateStoreStarRating(store);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult updateReview(Long id, String email, ReviewInput reviewInput) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (!optionalReview.isPresent()) {
            return ServiceResult.fail("리뷰가 존재하지 않습니다.");
        }
        Review review = optionalReview.get();

        if (!review.getMember().getEmail().equals(email)) {
            return ServiceResult.fail("리뷰 작성자만 수정이 가능합니다.");
        }

        review.setStarRating(reviewInput.getStarRating());
        review.setContents(reviewInput.getContents());
        review.setUpdateDate(LocalDateTime.now());
        reviewRepository.save(review);

        updateStoreStarRating(review.getStore());

        return ServiceResult.success();
    }

    @Override
    public Page<ReviewResult> getReviewListByStore(Long id) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            throw new BizException("매장이 존재하지 않습니다.");
        }
        Store store = optionalStore.get();

        Pageable limit = PageRequest.of(0, 10);
        Page<Review> reviewList = reviewRepository.findByStore(store, limit);

        return reviewList.map(r -> ReviewResult.toReviewResult(r));
    }

    private void updateStoreStarRating(Store store) {
        List<Review> reviewList = reviewRepository.findAllByStore(store);
        double averageStarRating = reviewList.stream().mapToDouble(r -> r.getStarRating()).average().getAsDouble();
        store.setStarRating(averageStarRating);
        storeRepository.save(store);
        log.info("별점 업데이트 : " + store.getName());
    }

}
