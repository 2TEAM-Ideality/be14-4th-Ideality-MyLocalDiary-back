package com.leesang.mylocaldiary.common.response;

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
}
