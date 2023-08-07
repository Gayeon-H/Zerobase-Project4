package com.zerobase.reservation.common.model;

import org.springframework.http.ResponseEntity;

public class ResponseResult {

    public static ResponseEntity<?> fail(String message, Object data) {

        return ResponseEntity.badRequest().body(ResponseMessage.fail(message, data));
    }

    public static ResponseEntity<?> fail(String message) {
        return fail(message, null);
    }

    public static ResponseEntity<?> success(Object data) {

        return ResponseEntity.ok().body(ResponseMessage.success(data));
    }

    public static ResponseEntity<?> success() {

        return success(null);
    }

    public static ResponseEntity<?> result(ServiceResult serviceResult) {
        if (serviceResult.isFail()) {
            return fail(serviceResult.getMessage());
        }

        return success(serviceResult.getMessage());
    }
}
