package com.zerobase.reservation.store.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {

    @NotBlank(message = "위치는 필수 항목입니다.")
    private String address;

    @NotBlank(message = "위도는 필수 항목입니다.")
    private double latitude;

    @NotBlank(message = "경도는 필수 항목입니다.")
    private double longitude;

}
