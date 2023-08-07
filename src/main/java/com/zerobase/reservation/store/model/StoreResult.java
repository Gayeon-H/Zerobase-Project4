package com.zerobase.reservation.store.model;

import com.zerobase.reservation.store.entity.Store;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreResult {

    private String name;
    private double starRating;
    private String contents;
    private String storeContact;
    private String address;
    private String managerName;

    public static StoreResult toStoreResult(Store store) {
        return StoreResult.builder()
                .name(store.getName())
                .starRating(store.getStarRating())
                .contents(store.getContents())
                .storeContact(store.getStoreContact())
                .address(store.getAddress())
                .managerName(store.getManagerName())
                .build();
    }

}
