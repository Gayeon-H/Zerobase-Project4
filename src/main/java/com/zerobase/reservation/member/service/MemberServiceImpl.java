package com.zerobase.reservation.member.service;


import com.zerobase.reservation.common.exception.BizException;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.member.model.LoginInput;
import com.zerobase.reservation.member.model.MemberInput;
import com.zerobase.reservation.member.model.MemberLoginToken;
import com.zerobase.reservation.member.model.MemberUpdateInput;
import com.zerobase.reservation.member.repository.MemberRepository;
import com.zerobase.reservation.util.JWTUtils;
import com.zerobase.reservation.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public ServiceResult addMember(MemberInput memberInput) {
        if (memberRepository.countByEmail(memberInput.getEmail()) > 0) {
            return ServiceResult.fail("이미 존재하는 이메일입니다.");
        }

        memberRepository.save(Member.builder()
                .name(memberInput.getName())
                .email(memberInput.getEmail())
                .password(PasswordUtils.encryptedPassword(memberInput.getPassword()))
                .phone(memberInput.getPhone())
                .role(memberInput.getRole())
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    @Override
    public MemberLoginToken login(LoginInput loginInput) {
        Optional<Member> optionalMember = memberRepository.findByEmail(loginInput.getEmail());
        if (!optionalMember.isPresent()) {
            throw new BizException("회원 정보가 존재하지 않거나 입력값이 정확하지 않습니다.");
        }
        Member member = optionalMember.get();

        if (!PasswordUtils.equalPassword(loginInput.getPassword(), member.getPassword())) {
            throw new BizException("회원 정보가 존재하지 않거나 입력값이 정확하지 않습니다.");
        }

        return JWTUtils.createToken(member);
    }

    @Override
    public ServiceResult updateMember(Long id, String email, MemberUpdateInput memberUpdateInput) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (!optionalMember.isPresent()) {
            return ServiceResult.fail("회원이 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        if (member.getId() != id) {
            return ServiceResult.fail("본인만 회원 정보 수정이 가능합니다.");
        }

        member.setName(memberUpdateInput.getName());
        member.setPassword(PasswordUtils.encryptedPassword(memberUpdateInput.getPassword()));
        member.setPhone(memberUpdateInput.getPhone());
        member.setUpdateDate(LocalDateTime.now());
        memberRepository.save(member);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult deleteMember(Long id, String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (!optionalMember.isPresent()) {
            return ServiceResult.fail("회원이 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        if (member.getId() != id) {
            return ServiceResult.fail("본인만 회원 탈퇴가 가능합니다.");
        }

        memberRepository.delete(member);

        return ServiceResult.success();
    }

}
