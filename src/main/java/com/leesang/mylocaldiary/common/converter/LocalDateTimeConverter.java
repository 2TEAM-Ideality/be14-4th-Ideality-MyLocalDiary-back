package com.leesang.mylocaldiary.common.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {
	public static String toDbFormat(String korDate) {
		LocalDate date = LocalDate.parse(korDate.replace(".", "-"));
		return date + " 00:00:00";  // 'yyyy-MM-dd 00:00:00' 형태
	}
}