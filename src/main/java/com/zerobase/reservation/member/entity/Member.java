package com.zerobase.reservation.member.entity;

import com.zerobase.reservation.member.type.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

}
