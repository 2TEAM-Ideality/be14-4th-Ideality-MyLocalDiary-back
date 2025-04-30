package com.leesang.mylocaldiary.common.converter;

public class ReportStatusConverter {
	public static String toEnumValue(String kor) {
		return switch (kor) {
			case "처리중" -> "WAITING";
			case "처리완료" -> "RESOLVED";
			case "반려" -> "REJECTED";
			default -> throw new IllegalArgumentException("Unknown Report Status: " + kor);
		};
	}
}
