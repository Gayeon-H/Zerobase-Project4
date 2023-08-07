package com.zerobase.reservation.common.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zerobase.reservation.common.exception.AuthFailException;
import com.zerobase.reservation.util.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!validJWT(request)) {
            throw new AuthFailException("인증정보가 정확하지 않습니다.");
        }

        return true;
    }

    private boolean validJWT(HttpServletRequest request) {
        String token = request.getHeader("Z-TOKEN");
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return false;
        }

        return true;
    }
}
