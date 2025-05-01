package com.leesang.mylocaldiary.member.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtherMemberInfoDTO {
	private Long id;
	private String nickname;
	private String bio;
	private String profileImage;
	private String profileMusic;
	private Integer isPublic;
}
