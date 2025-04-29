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
public class RequestReportDTO {
	private String reportType;
	private int reportedId;
	private String content;
	private int memberId;
	private int reportReasonId;
}
