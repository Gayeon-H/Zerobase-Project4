package com.zerobase.reservation.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    GENERAL("ROLE_GENERAL"),
    PARTNER("ROLE_PARTNER");

    private String value;

}
