package com.leesang.mylocaldiary.common.response;

import java.util.List;

import com.leesang.mylocaldiary.admin.dto.ReportResponseDTO;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class CommonResponseVO<T> {
    /* 설명. API 설계를 위한 공통의 설계 */
    private int status;
    private String message;
    private T data;

    // 성공 응답 생성 메서드
    public static <T> CommonResponseVO<T> success(String message, T data) {
        return new CommonResponseVO<>(200, message, data);
    }

    // 실패 응답 생성 메서드
    public static <T> CommonResponseVO<T> fail(int status, String message) {
        return new CommonResponseVO<>(status, message, null);
    }
}
