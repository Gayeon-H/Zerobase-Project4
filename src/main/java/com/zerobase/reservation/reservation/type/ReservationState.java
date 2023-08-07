package com.zerobase.reservation.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationState {

    WAIT_APPROVE("승인 대기"),
    RESERVATION_REFUSED("예약 거절"),
    RESERVATION_APPROVED("예약 승인"),
    CHECK_VISIT("방문 완료"),
    NOT_USE("이용 안함");

    private String state;

}
