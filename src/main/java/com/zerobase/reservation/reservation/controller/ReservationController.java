package com.zerobase.reservation.reservation.controller;

import com.zerobase.reservation.common.exception.BizException;
import com.zerobase.reservation.common.model.ResponseResult;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.reservation.model.CheckVisitInput;
import com.zerobase.reservation.reservation.model.ReservationInput;
import com.zerobase.reservation.reservation.model.ReservationResult;
import com.zerobase.reservation.reservation.model.ReservationStateInput;
import com.zerobase.reservation.reservation.service.ReservationService;
import com.zerobase.reservation.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reserve/store/{id}")
    public ResponseEntity<?> reserveStore(@PathVariable Long id,
                                          @RequestHeader("Z-TOKEN") String token,
                                          @RequestBody @Valid ReservationInput reservationInput) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = reservationService.reserveStore(id, email, reservationInput);

        return ResponseResult.result(serviceResult);
    }

    @GetMapping("/reservation/list")
    public ResponseEntity<?> getReservations(@RequestHeader("Z-TOKEN") String token) {
        String email = JWTUtils.getIssuer(token);
        Page<ReservationResult> reservationList = null;
        try {
            reservationList = reservationService.getReservationList(email);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }

        if (reservationList == null) {
            return ResponseResult.success(Page.empty());
        }

        return ResponseResult.success(reservationList);
    }

    @GetMapping("/partner/reservation/store/{id}")
    public ResponseEntity<?> getReservationList(@PathVariable Long id,
                                                @RequestHeader("Z-TOKEN") String token) {
        String email = JWTUtils.getIssuer(token);
        Page<ReservationResult> reservationList = null;
        try {
            reservationList = reservationService.getReservationListByStore(id, email);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }

        if (reservationList == null) {
            return ResponseResult.success(Page.empty());
        }

        return ResponseResult.success(reservationList);
    }

    @PatchMapping("/partner/reservation/state/{id}")
    public ResponseEntity<?> approveOrRefuseReservation(@PathVariable Long id,
                                                        @RequestHeader("Z-TOKEN") String token,
                                                        @RequestBody @Valid ReservationStateInput reservationStateInput) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = reservationService.approveOrRefuseReservation(id, email, reservationStateInput);

        return ResponseResult.result(serviceResult);
    }

    @PatchMapping("/public/reservation/store/arrive")
    public ResponseEntity<?> checkVisit(@RequestBody @Valid CheckVisitInput checkVisitInput) {
        //TODO: +도착정보 알림
        ServiceResult serviceResult = reservationService.checkVisit(checkVisitInput);

        return ResponseResult.result(serviceResult);
    }

}
