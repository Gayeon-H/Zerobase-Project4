package com.zerobase.reservation.reservation.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckVisitInput {

    @NotBlank(message = "이용자명은 필수 입력 사항입니다.")
    private String userName;

    @Size(max = 20, message = "'-'포함 입력해주세요.")
    @NotBlank(message = "이용자 연락처는 필수 입력 사항입니다.")
    private String userPhone;

}
