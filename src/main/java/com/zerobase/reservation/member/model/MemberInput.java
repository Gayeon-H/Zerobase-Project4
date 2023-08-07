package com.zerobase.reservation.member.model;

import com.zerobase.reservation.member.type.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInput {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @Size(max = 20, message = "'-'포함 입력해주세요.")
    @NotBlank(message = "연락처는 필수 항목입니다.")
    private String phone;

    @NotBlank(message = "일반 멤버와 파트너 멤버 중에 선택해주세요.")
    private Role role;

}
