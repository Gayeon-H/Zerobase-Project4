package com.zerobase.reservation.review.controller;


import com.zerobase.reservation.common.model.ResponseResult;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.review.model.ReviewInput;
import com.zerobase.reservation.review.model.ReviewResult;
import com.zerobase.reservation.review.service.ReviewService;
import com.zerobase.reservation.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/reservation/{id}")
    public ResponseEntity<?> addReview(@PathVariable Long id,
                                       @RequestHeader("Z-TOKEN") String token,
                                       @RequestBody ReviewInput reviewInput) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = reviewService.addReview(id, email, reviewInput);

        return ResponseResult.result(serviceResult);
    }

    @PutMapping("/review/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id,
                                          @RequestHeader("Z-TOKEN") String token,
                                          @RequestBody ReviewInput reviewInput) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = reviewService.updateReview(id, email, reviewInput);

        return ResponseResult.result(serviceResult);
    }

    @GetMapping("/public/review/store/{id}")
    public ResponseEntity<?> getReviewListByStore(@PathVariable Long id) {
        Page<ReviewResult> reviewList = reviewService.getReviewListByStore(id);

        return ResponseResult.success(reviewList);
    }

}
