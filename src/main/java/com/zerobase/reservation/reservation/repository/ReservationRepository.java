package com.zerobase.reservation.reservation.repository;

import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByUserNameAndUserPhone(String userName, String userPhone);

    Page<Reservation> findAllByReservationMemberOrderByReservationDate(Member reservationMember, Pageable limit);

    Page<Reservation> findAllByStoreOrderByReservationDate(Store store, Pageable limit);

}
