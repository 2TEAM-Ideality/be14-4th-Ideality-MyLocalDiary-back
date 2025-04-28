package com.leesang.mylocaldiary.common.converter;

public class ReportTypeConverter {
	public static String toEnumValue(String kor) {
		return switch (kor) {
			case "게시글" -> "POST";
			case "댓글" -> "COMMENT";
			case "회원" -> "MEMBER";
			default -> throw new IllegalArgumentException("Unknown Report Type: " + kor);
		};
	}

}
