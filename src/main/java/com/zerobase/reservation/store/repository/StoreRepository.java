package com.zerobase.reservation.store.repository;

import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    long countByNameAndAddress(String name, String location);

    Page<Store> findByNameStartingWithIgnoreCase(String keyword, Pageable limit);

    List<Store> findAllByRegMember(Member regMember);

}
