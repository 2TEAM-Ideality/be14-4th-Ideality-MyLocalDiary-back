package com.leesang.mylocaldiary.admin.aggregate;

public enum SuspensionType {
	AUTO("시스템 자동"),
	MANUAL("관리자 수동");


	private final String description;

	SuspensionType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
