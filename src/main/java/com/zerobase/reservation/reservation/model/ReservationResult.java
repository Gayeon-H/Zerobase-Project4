package com.zerobase.reservation.reservation.model;

import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.type.ReservationState;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResult {

    private String storeName;
    private String storeAddress;
    private String storeContact;

    private String reserveMemberName;
    private String reserveMemberPhone;

    private String userName;
    private String userPhone;
    private int numberOfPeople;
    private LocalDateTime reservationDate;
    private ReservationState reservationState;

    public static ReservationResult toReservationResult(Reservation r) {
        return ReservationResult.builder()
                .storeName(r.getStore().getName())
                .storeAddress(r.getStore().getAddress())
                .storeContact(r.getStore().getStoreContact())
                .reserveMemberName(r.getReservationMember().getName())
                .reserveMemberPhone(r.getReservationMember().getPhone())
                .userName(r.getUserName())
                .userPhone(r.getUserPhone())
                .numberOfPeople(r.getNumberOfPeople())
                .reservationDate(r.getReservationDate())
                .reservationState(r.getReservationState())
                .build();
    }

}
