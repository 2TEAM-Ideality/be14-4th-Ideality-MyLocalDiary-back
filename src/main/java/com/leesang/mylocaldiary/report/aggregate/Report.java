package com.leesang.mylocaldiary.report.aggregate;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Report {

    private Integer id;                  // 신고 ID (PK)
    private LocalDateTime createdAt;  // 신고 생성일
    private String reportType;        // 신고 종류 (POST, COMMENT 등)
    private Integer reportedId;          // 신고된 게시글ID나 댓글ID
    private String content;           // 신고 상세내용
    private String status;            // 신고 상태 (예: 처리 완료/거절 등)
    private Integer memberId;            // 신고한 회원 ID
    private Integer reportReasonId;      // 신고 사유 ID

}
