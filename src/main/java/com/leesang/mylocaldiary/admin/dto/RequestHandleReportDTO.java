package com.leesang.mylocaldiary.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RequestHandleReportDTO {
	private int id;		// 신고 아이디
	private int memberId;	// 신고 대상 회원
}
