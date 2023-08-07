package com.zerobase.reservation.common.handler;

import com.zerobase.reservation.common.exception.AuthFailException;
import com.zerobase.reservation.common.exception.BizException;
import com.zerobase.reservation.common.model.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthFailException.class)
    public ResponseEntity<?> authFailExceptionHandler(AuthFailException e) {
        return ResponseResult.fail("[인증실패] : " + e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<?> bizExceptionHandler(BizException e) {
        return ResponseResult.fail(e.getMessage());
    }

}
