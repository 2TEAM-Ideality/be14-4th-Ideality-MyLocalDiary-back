package com.leesang.mylocaldiary.member.mybatis.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchMemberDTO {
    private Integer memberId;
    private String nickname;
    private String name;
    private String profileImage;
    private Boolean isPublic;
    private String bio;
    private Boolean followStatus; // TRUE: 팔로잉, FALSE: 대기, NULL: 안함

    public String getStatusText() {
        if (followStatus == null) return "팔로우";
        if (followStatus) return "팔로잉";
        return "수락 대기";
    }
}
