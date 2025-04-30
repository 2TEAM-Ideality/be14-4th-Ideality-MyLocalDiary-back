package com.leesang.mylocaldiary.admin.aggregate;

import java.time.LocalDateTime;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name="report_type", nullable = false, length = 20)
	private ReportType reportType;  // '게시글', '댓글', '회원' 구분

	@Column(name="reported_id", nullable = false)
	private int reportedId; // 신고 대상 ID (게시글ID, 댓글ID, 회원ID 등)

	@Column(name="content",  nullable = false, length = 255)
	private String content; // 신고 내용

	@Enumerated(EnumType.STRING)
	@Column(name="status", nullable = false, length = 20)
	private ReportStatus status; // 처리 상태 (WAITING, RESOLVED, REJECTED 등)

	@Column(name="created_at", nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="member_id", referencedColumnName = "id", nullable = false)
	private MemberEntity member;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "report_reason_id", referencedColumnName = "id", nullable = false)
	private ReportReasonEntity reportReason; // 신고 사유 연결


	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	public void setStatus(ReportStatus reportStatus) {
		this.status = reportStatus;
	}
}
