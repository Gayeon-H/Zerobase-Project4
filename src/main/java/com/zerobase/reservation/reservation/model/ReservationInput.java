package com.zerobase.reservation.reservation.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInput {

    @NotBlank(message = "예약자명은 필수 입력사항입니다.")
    private String userName;

    @Size(max = 11, message = "'-'없이 입력해주세요.")
    @NotBlank(message = "예약자 연락처는 필수 입력사항입니다.")
    private String userPhone;

    @NotBlank(message = "예약인원수는 필수 입력사항입니다.")
    private int numberOfPeople;

    @NotBlank(message = "예약일은 필수 입력사항입니다.")
    private LocalDateTime reservationDate;

}
