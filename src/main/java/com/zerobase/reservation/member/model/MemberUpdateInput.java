package com.zerobase.reservation.member.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateInput {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @Size(max = 20, message = "'-'포함 입력해주세요.")
    @NotBlank(message = "연락처는 필수 항목입니다.")
    private String phone;

}
