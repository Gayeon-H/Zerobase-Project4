package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.common.exception.BizException;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.member.repository.MemberRepository;
import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.model.CheckVisitInput;
import com.zerobase.reservation.reservation.model.ReservationInput;
import com.zerobase.reservation.reservation.model.ReservationResult;
import com.zerobase.reservation.reservation.model.ReservationStateInput;
import com.zerobase.reservation.reservation.repository.ReservationRepository;
import com.zerobase.reservation.reservation.type.ReservationState;
import com.zerobase.reservation.store.entity.Store;
import com.zerobase.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override
    public ServiceResult reserveStore(Long id, String email, ReservationInput reservationInput) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (!optionalMember.isPresent()) {
            return ServiceResult.fail("존재하지 않는 회원입니다.");
        }
        Member member = optionalMember.get();

        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            return ServiceResult.fail("존재하지 않는 매장입니다.");
        }
        Store store = optionalStore.get();

        reservationRepository.save(Reservation.builder()
                .store(store)
                .reservationMember(member)
                .userName(reservationInput.getUserName())
                .userPhone(reservationInput.getUserPhone())
                .numberOfPeople(reservationInput.getNumberOfPeople())
                .reservationDate(reservationInput.getReservationDate())
                .reservationState(ReservationState.WAIT_APPROVE)
                .build());

        return ServiceResult.success();
    }

    @Override
    public Page<ReservationResult> getReservationList(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (!optionalMember.isPresent()) {
            throw new BizException("존재하지 않는 회원입니다.");
        }
        Member member = optionalMember.get();

        Pageable limit = PageRequest.of(0, 10);
        Page<Reservation> reservationList = reservationRepository.findAllByReservationMemberOrderByReservationDate(member, limit);

        return reservationList.map(r -> ReservationResult.toReservationResult(r));
    }

    @Override
    public Page<ReservationResult> getReservationListByStore(Long id, String email) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            throw new BizException("존재하지 않는 매장입니다.");
        }
        Store store = optionalStore.get();

        if (!store.getRegMember().getEmail().equals(email)) {
            throw new BizException("매장을 등록한 파트너 회원만 예약 목록을 조회할 수 있습니다.");
        }

        Pageable limit = PageRequest.of(0, 10);
        Page<Reservation> reservationList = reservationRepository.findAllByStoreOrderByReservationDate(store, limit);

        return reservationList.map(r -> ReservationResult.toReservationResult(r));
    }

    @Override
    public ServiceResult approveOrRefuseReservation(Long id, String email, ReservationStateInput reservationStateInput) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (!optionalReservation.isPresent()) {
            return ServiceResult.fail("존재하지 않은 예약입니다.");
        }
        Reservation reservation = optionalReservation.get();

        if (!reservation.getStore().getRegMember().getEmail().equals(email)) {
            return ServiceResult.fail("해당 매장을 등록한 파트너 회원만 예약 상태 변경이 가능합니다.");
        }

        if (reservation.getReservationState().equals(ReservationState.CHECK_VISIT)
                || reservation.getReservationState().equals(ReservationState.NOT_USE)) {
            return ServiceResult.fail("이미 방문했거나 예약 시간이 지난 예약은 변경할 수 없습니다.");
        }

        reservation.setReservationState(reservationStateInput.getReservationState());
        reservationRepository.save(reservation);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult checkVisit(CheckVisitInput checkVisitInput) {
        Optional<Reservation> optionalReservation = reservationRepository
                .findByUserNameAndUserPhone(checkVisitInput.getUserName(), checkVisitInput.getUserPhone());
        if (!optionalReservation.isPresent()) {
            return ServiceResult.fail("존재하지 않은 예약입니다.");
        }
        Reservation reservation = optionalReservation.get();

        if (!reservation.getReservationState().equals(ReservationState.RESERVATION_APPROVED)) {
            return ServiceResult.fail("예약이 승인되지 않았거나 이미 완료된 예약입니다.");
        }

        if (!reservation.getReservationDate().isBefore(LocalDateTime.now().minusMinutes(10))) {
            reservation.setReservationState(ReservationState.NOT_USE);
            reservationRepository.save(reservation);

            return ServiceResult.fail("매장에 10분 전에 도착하지 못해 매장 이용이 제한됩니다.");
        }

        reservation.setReservationState(ReservationState.CHECK_VISIT);
        reservationRepository.save(reservation);

        return ServiceResult.success();
    }

}
