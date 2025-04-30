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
public class ReportDTO {
	private int id;
	private String createdAt;
	private String reportType;
	private int reportedId;
	private String content;
	private String status;
	private int memberId;
	private int reportReasonId;
}



 // example
// 	{
	//  "id": "1",
	//  "created_at": "2025.03.13",
	//  "report_type": "게시글",
	//  "reported_id": 1,
	//  "content": "이 게시글은 부적절한 내용을 포함하고 있습니다.",
	//  "status": "처리완료",
	//  "member_id": 3,
	//  "report_reason_id": "9"
	//  },