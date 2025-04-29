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
public class SuspensionDTO {
	private int id;
	private String suspensionStartDate;
	private String suspensionEndDate;
	private String type;
	private int memberId;
}
 // {
	//  "id": "1",
	//  "start_date": "2025-04-01",
	//  "end_date": "2025-04-30",
	//  "type": "회원",
	//  "target_member_id": 3
	//  },