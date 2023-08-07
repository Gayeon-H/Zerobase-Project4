package com.zerobase.reservation.reservation.entity;

import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.reservation.type.ReservationState;
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
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Store store;

    @ManyToOne
    @JoinColumn
    private Member reservationMember;

    private String userName;
    private String userPhone;
    private int numberOfPeople;
    private LocalDateTime reservationDate;
    private ReservationState reservationState;

}
