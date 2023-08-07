package com.zerobase.reservation.review.model;

import com.zerobase.reservation.review.entity.Review;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResult {

    private String userName;
    private double starRating;
    private String contents;
    private LocalDateTime Date;

    public static ReviewResult toReviewResult(Review r) {
        return ReviewResult.builder()
                .userName(r.getMember().getName())
                .starRating(r.getStarRating())
                .contents(r.getContents())
                .Date(r.getUpdateDate() != null ? r.getUpdateDate() : r.getRegDate())
                .build();
    }

}
