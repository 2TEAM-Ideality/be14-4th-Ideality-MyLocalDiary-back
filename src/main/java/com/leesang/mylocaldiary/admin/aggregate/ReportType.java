package com.leesang.mylocaldiary.admin.aggregate;

public enum ReportType {
	POST("게시글"),
	COMMENT("댓글"),
	MEMBER("회원");

	private final String description;

	ReportType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
