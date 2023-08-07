package com.zerobase.reservation.review.entity;

import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.store.entity.Store;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Member Member;

    @ManyToOne
    @JoinColumn
    private Store store;

    private double starRating;
    private String contents;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

}
