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
public class SuspensionUpdateRequestDTO {
	private int id;
	private String suspensionStartDate;
	private String suspensionEndDate;
	private String type;
	private int memberId;

}
