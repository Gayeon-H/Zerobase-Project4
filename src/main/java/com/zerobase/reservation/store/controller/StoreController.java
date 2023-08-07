package com.zerobase.reservation.store.controller;


import com.zerobase.reservation.common.model.ResponseResult;
import com.zerobase.reservation.common.model.ServiceResult;
import com.zerobase.reservation.store.entity.Store;
import com.zerobase.reservation.store.model.StoreInput;
import com.zerobase.reservation.store.model.StoreResult;
import com.zerobase.reservation.store.service.StoreService;
import com.zerobase.reservation.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/partner/store/register")
    public ResponseEntity<?> addStore(@RequestBody @Valid StoreInput storeInput,
                                      @RequestHeader("Z-TOKEN") String token) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = storeService.addStore(email, storeInput);

        return ResponseResult.result(serviceResult);
    }

    @PutMapping("/partner/store/{id}")
    public ResponseEntity<?> updateStore(@PathVariable Long id,
                                          @RequestHeader("Z-TOKEN") String token,
                                          @RequestBody @Valid StoreInput storeInput) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = storeService.updateStore(id, email, storeInput);

        return ResponseResult.result(serviceResult);
    }

    @DeleteMapping("/partner/store/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable Long id,
                                          @RequestHeader("Z-TOKEN") String token) {
        String email = JWTUtils.getIssuer(token);
        ServiceResult serviceResult = storeService.deleteStore(id, email);

        return ResponseResult.result(serviceResult);
    }

    @GetMapping("/public/store/search")
    public ResponseEntity<?> searchStoreByKeyword(@RequestParam String keyword) {
        List<String> storeKeywordList = storeService.getStoreNamesByKeyword(keyword);

        return ResponseResult.success(storeKeywordList);
    }

    @GetMapping("/public/store/alphabet")
    public ResponseEntity<?> getListByAlphabet() {
        Page<StoreResult> storeList = storeService.getStoreListByAlphabet();

        return ResponseResult.success(storeList);
    }

    @GetMapping("/public/store/star-rating")
    public ResponseEntity<?> getListByStarRating() {
        Page<StoreResult> storeList = storeService.getStoreListByStarRating();

        return ResponseResult.success(storeList);
    }

    @GetMapping("/public/store/distance")
    public ResponseEntity<?> getListByDistance(@RequestBody double curLatitude, double curLongitude) {
        Page<StoreResult> storeList = storeService.getStoreListByDistance(curLatitude, curLongitude);

        return ResponseResult.success(storeList);
    }

    @GetMapping("/public/store/{id}")
    public ResponseEntity<?> getStore(@PathVariable Long id) {
        StoreResult store = storeService.getStore(id);

        return ResponseResult.success(store);
    }

    @GetMapping("/partner/stores")
    public ResponseEntity<?> getStoreList(@RequestHeader("Z-TOKEN") String token) {
        String email = JWTUtils.getIssuer(token);
        List<Store> storeList = storeService.getStoreList(email);

        return ResponseResult.success();
    }

}
