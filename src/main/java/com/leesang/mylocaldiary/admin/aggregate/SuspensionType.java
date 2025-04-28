package com.leesang.mylocaldiary.admin.aggregate;

public enum SuspensionType {
	ONE("예시1"),
	TWO("예시2");


	private final String description;

	SuspensionType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
