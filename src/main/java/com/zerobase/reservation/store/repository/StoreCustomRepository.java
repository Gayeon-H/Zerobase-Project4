package com.zerobase.reservation.store.repository;

import com.zerobase.reservation.store.entity.Store;
import com.zerobase.reservation.store.model.StoreResult;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class StoreCustomRepository {

    private final EntityManager entityManager;

    public Page<StoreResult> findAllOrderByDistance(double curLatitude, double curLongitude, Pageable limit) {
        String sql = "s.*, (6371 * acos(cos(radians(" + curLatitude + "))*cos(radians(s.latitude))*cos(radians(s.longitude)" +
                " - radians(" + curLongitude + ")) + sin(radians(" + curLatitude + ")) * sin(radians(s.latitude)))) as distance " +
                "from store s order by distance";

        Query nativeQuery = entityManager.createNativeQuery(sql);

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<Store> storeList = jpaResultMapper.list(nativeQuery, Store.class);
        List<StoreResult> storeResultList = storeList.stream()
                .map(s -> StoreResult.toStoreResult(s))
                .collect(Collectors.toList());

        return new PageImpl<>(storeResultList);
    }

}
