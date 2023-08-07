package com.zerobase.reservation.review.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInput {

    private double starRating;
    private String contents;

}
