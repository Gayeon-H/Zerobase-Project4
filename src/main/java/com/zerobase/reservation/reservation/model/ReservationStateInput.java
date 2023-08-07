package com.zerobase.reservation.reservation.model;

import com.zerobase.reservation.reservation.type.ReservationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class ReservationStateInput {

    @NotNull
    private ReservationState reservationState;

}
