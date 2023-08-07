package com.zerobase.reservation.member.controller;


import com.zerobase.reservation.common.exception.BizException;
import com.zerobase.reservation.common.model.ResponseResult;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.member.model.LoginInput;
import com.zerobase.reservation.member.model.MemberInput;
import com.zerobase.reservation.member.model.MemberLoginToken;
import com.zerobase.reservation.member.model.MemberUpdateInput;
import com.zerobase.reservation.member.service.MemberService;
import com.zerobase.reservation.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/public/member/join")
    public ResponseEntity<?> addMember(@RequestBody @Valid MemberInput memberInput) {

        ServiceResult serviceResult = memberService.addMember(memberInput);

        return ResponseResult.result(serviceResult);
    }

    @PutMapping("/member/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id,
                                          @RequestHeader("Z-TOKEN") String token,
                                          @RequestBody @Valid MemberUpdateInput memberUpdateInput) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = memberService.updateMember(id, email, memberUpdateInput);

        return ResponseResult.result(serviceResult);
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id,
                                          @RequestHeader("Z-TOKEN") String token) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = memberService.deleteMember(id, email);

        return ResponseResult.result(serviceResult);
    }

    @PostMapping("/public/member/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginInput loginInput) {
        MemberLoginToken loginToken = null;
        try {
            loginToken = memberService.login(loginInput);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }

        if (loginToken == null) {
            return ResponseResult.fail("토큰 정보 생성에 실패하였습니다.");
        }

        return ResponseResult.success(memberService.login(loginInput));
    }

}
