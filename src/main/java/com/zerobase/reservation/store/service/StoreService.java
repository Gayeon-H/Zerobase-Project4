package com.zerobase.reservation.store.service;

import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.store.entity.Store;
import com.zerobase.reservation.store.model.StoreInput;
import com.zerobase.reservation.store.model.StoreResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoreService {

    /**
     * 매장 등록(파트너 회원인지 확인 후)
     */
    ServiceResult addStore(String email, StoreInput storeInput);

    /**
     * 특정 키워드로 시작하는 매장명 검색
     */
    List<String> getStoreNamesByKeyword(String keyword);

    /**
     * 매장 상세 정보 조회
     */
    StoreResult getStore(Long id);

    /**
     * 가나다 순 매장 목록 조회
     */
    Page<StoreResult> getStoreListByAlphabet();

    /**
     * 별점 순 매장 목록 조회
     */
    Page<StoreResult> getStoreListByStarRating();

    /**
     * 거리 순 매장 목록 조회
     */
    Page<StoreResult> getStoreListByDistance(double curLatitude, double curLongitude);

    /**
     * 매장 정보 수정(파트너 회원인지 확인 후)
     */
    ServiceResult updateStore(Long id, String email, StoreInput storeInput);

    /**
     * 매장 정보 삭제(파트너 회원인지 확인 후)
     */
    ServiceResult deleteStore(Long id, String email);

    /**
     * 등록 매장 목록 조회(파트너 회원인지 확인 후)
     */
    List<Store> getStoreList(String email);

}
