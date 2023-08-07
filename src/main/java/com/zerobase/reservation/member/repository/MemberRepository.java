package com.zerobase.reservation.member.repository;

import com.zerobase.reservation.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    long countByEmail(String email);

    Optional<Member> findByEmail(String email);
}
