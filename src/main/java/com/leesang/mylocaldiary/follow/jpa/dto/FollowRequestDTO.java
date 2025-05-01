package com.leesang.mylocaldiary.follow.jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowRequestDTO {
    private Long followingMemberId;
    private Long followTargetMemberId;
}
