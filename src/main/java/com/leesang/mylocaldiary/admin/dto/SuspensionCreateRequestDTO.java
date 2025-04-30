package com.leesang.mylocaldiary.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuspensionCreateRequestDTO {
	private Long memberId;
	private String suspensionStartDate;
	private String suspensionEndDate;
	private String type;
}