package com.zerobase.reservation.member.service;

import com.zerobase.reservation.member.model.LoginInput;
import com.zerobase.reservation.member.model.MemberInput;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.member.model.MemberLoginToken;
import com.zerobase.reservation.member.model.MemberUpdateInput;

public interface MemberService {

    /**
     * 회원가입
     */
    ServiceResult addMember(MemberInput memberInput);

    /**
     * 로그인
     */
    MemberLoginToken login(LoginInput loginInput);

    /**
     * 회원 정보 수정
     */
    ServiceResult updateMember(Long id, String email, MemberUpdateInput memberUpdateInput);

    /**
     * 회원 탈퇴
     */
    ServiceResult deleteMember(Long id, String email);

}
