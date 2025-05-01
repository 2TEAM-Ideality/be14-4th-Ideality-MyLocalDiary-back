package com.leesang.mylocaldiary.stamp.jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterStampDTO {
    private Long targetMemberId;
    private Long stampId;
}
