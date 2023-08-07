package com.zerobase.reservation.store.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreInput {

    @NotBlank(message = "매장 이름은 필수 항목입니다.")
    private String name;

    @Size(min = 10, max = 250, message = "상세 정보는 최대 250자까지 작성가능합니다.")
    @NotBlank(message = "매장 상세정보는 10자 이상 작성해주세요.")
    private String contents;

    @Size(max = 20, message = "'-'포함 입력해주세요.")
    @NotBlank(message = "매장 연락처는 필수 항목입니다.")
    private String storeContact;

    @Valid
    private Location location;

    @NotBlank(message = "담당 매니저 이름은 필수 항목입니다.")
    private String managerName;

}
