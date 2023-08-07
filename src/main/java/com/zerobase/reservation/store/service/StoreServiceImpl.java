package com.zerobase.reservation.store.service;

import com.zerobase.reservation.common.exception.BizException;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.member.entity.Member;
import com.zerobase.reservation.member.repository.MemberRepository;
import com.zerobase.reservation.member.type.Role;
import com.zerobase.reservation.store.entity.Store;
import com.zerobase.reservation.store.model.StoreInput;
import com.zerobase.reservation.store.model.StoreResult;
import com.zerobase.reservation.store.repository.StoreCustomRepository;
import com.zerobase.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreCustomRepository storeCustomRepository;
    private final MemberRepository memberRepository;

    @Override
    public ServiceResult addStore(String email, StoreInput storeInput) {
        if (storeRepository.countByNameAndAddress(storeInput.getName(), storeInput.getLocation().getAddress()) > 0) {
            return ServiceResult.fail("이미 등록된 매장입니다.");
        }

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (!optionalMember.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        if (member.getRole() == Role.GENERAL) {
            return ServiceResult.fail("파트너 회원만 매장을 등록할 수 있습니다.");
        }

        storeRepository.save(Store.builder()
                .name(storeInput.getName())
                .contents(storeInput.getContents())
                .storeContact(storeInput.getStoreContact())
                .address(storeInput.getLocation().getAddress())
                .latitude(storeInput.getLocation().getLatitude())
                .longitude(storeInput.getLocation().getLongitude())
                .starRating(0.0)
                .managerName(storeInput.getManagerName())
                .regMember(member)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    @Override
    public List<String> getStoreNamesByKeyword(String keyword) {
        Pageable limit = PageRequest.of(0, 10);
        Page<Store> stores = storeRepository.findByNameStartingWithIgnoreCase(keyword, limit);

        return stores.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
    }

    @Override
    public StoreResult getStore(Long id) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            throw new BizException("매장이 존재하지 않습니다.");
        }
        Store store = optionalStore.get();

        return StoreResult.builder()
                .name(store.getName())
                .contents(store.getContents())
                .storeContact(store.getStoreContact())
                .address(store.getAddress())
                .managerName(store.getRegMember().getName())
                .build();
    }

    @Override
    public Page<StoreResult> getStoreListByAlphabet() {
        Pageable limit = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        Page<Store> stores = storeRepository.findAll(limit);

        return stores.map(s -> StoreResult.toStoreResult(s));
    }

    @Override
    public Page<StoreResult> getStoreListByStarRating() {
        Pageable limit = PageRequest.of(0, 10, Sort.Direction.DESC, "startRating");
        Page<Store> stores = storeRepository.findAll(limit);

        return stores.map(s -> StoreResult.toStoreResult(s));
    }

    @Override
    public Page<StoreResult> getStoreListByDistance(double curLatitude, double curLongitude) {
        Pageable limit = PageRequest.of(0, 10);

        return storeCustomRepository.findAllOrderByDistance(curLatitude, curLongitude, limit);
    }

    @Override
    public ServiceResult updateStore(Long id, String email, StoreInput storeInput) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            return ServiceResult.fail("매장이 존재하지 않습니다.");
        }
        Store store = optionalStore.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (!optionalMember.isPresent()) {
            return ServiceResult.fail("회원이 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if (store.getRegMember().getId() != member.getId()) {
            return ServiceResult.fail("이 매장을 등록한 파트너 회원만 매장 정보를 수정할 수 있습니다.");
        }

        store.setName(storeInput.getName());
        store.setContents(storeInput.getContents());
        store.setAddress(storeInput.getLocation().getAddress());
        store.setLatitude(storeInput.getLocation().getLatitude());
        store.setLongitude(storeInput.getLocation().getLongitude());
        store.setStoreContact(storeInput.getStoreContact());
        store.setManagerName(storeInput.getManagerName());
        store.setUpdateDate(LocalDateTime.now());
        storeRepository.save(store);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult deleteStore(Long id, String email) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            return ServiceResult.fail("매장이 존재하지 않습니다.");
        }
        Store store = optionalStore.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (!optionalMember.isPresent()) {
            return ServiceResult.fail("회원이 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if (store.getRegMember().getId() != member.getId()) {
            return ServiceResult.fail("이 매장을 등록한 파트너 회원만 매장 정보를 삭제할 수 있습니다.");
        }

        storeRepository.delete(store);

        return ServiceResult.success();
    }

    @Override
    public List<Store> getStoreList(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (!optionalMember.isPresent()) {
            throw new BizException("회원이 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if (member.getRole().equals(Role.GENERAL)) {
            throw new BizException("파트너 회원만 등록 매장을 조회할 수 있습니다.");
        }

        return storeRepository.findAllByRegMember(member);
    }
}
