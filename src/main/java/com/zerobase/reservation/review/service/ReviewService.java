package com.zerobase.reservation.review.service;

import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.review.model.ReviewInput;
import com.zerobase.reservation.review.model.ReviewResult;
import org.springframework.data.domain.Page;

public interface ReviewService {

    /**
     * 이용한 예약에 대해 리뷰 작성 및 매장 별점 업데이트
     */
    ServiceResult addReview(Long id, String email, ReviewInput reviewInput);

    /**
     * 리뷰 수정 및 매장 별점 업데이트
     */
    ServiceResult updateReview(Long id, String email, ReviewInput reviewInput);

    /**
     * 매장별 리뷰 목록 조회
     */
    Page<ReviewResult> getReviewListByStore(Long id);

}
