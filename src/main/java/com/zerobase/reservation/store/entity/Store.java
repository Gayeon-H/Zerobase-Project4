package com.zerobase.reservation.store.entity;

import com.zerobase.reservation.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contents;
    private String storeContact;

    private String address;
    private double latitude;
    private double longitude;

    private double starRating;
    private String managerName;

    @ManyToOne
    @JoinColumn
    private Member regMember;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

}
