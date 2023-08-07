package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.reservation.model.CheckVisitInput;
import com.zerobase.reservation.reservation.model.ReservationInput;
import com.zerobase.reservation.reservation.model.ReservationResult;
import com.zerobase.reservation.reservation.model.ReservationStateInput;
import org.springframework.data.domain.Page;

public interface ReservationService {

    /**
     * 매장 예약
     */
    ServiceResult reserveStore(Long id, String email, ReservationInput reservationInput);

    /**
     * 회원의 예약 내역 목록 조회
     */
    Page<ReservationResult> getReservationList(String email);

    /**
     * 매장 별 예약 내역 목록 조회(파트너 회원인지 확인 후)
     */
    Page<ReservationResult> getReservationListByStore(Long id, String email);

    /**
     * 예약 승인 또는 거절(파트너 회원인지 확인 후)
     */
    ServiceResult approveOrRefuseReservation(Long id, String email, ReservationStateInput reservationStateInput);

    /**
     * 사용자 이름과 연락처로 방문 확인
     */
    ServiceResult checkVisit(CheckVisitInput checkVisitInput);

}
