package com.leesang.mylocaldiary.admin.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leesang.mylocaldiary.admin.aggregate.ReportEntity;
import com.leesang.mylocaldiary.admin.aggregate.ReportReasonEntity;
import com.leesang.mylocaldiary.admin.aggregate.ReportStatus;
import com.leesang.mylocaldiary.admin.aggregate.ReportType;
import com.leesang.mylocaldiary.admin.aggregate.SuspensionEntity;
import com.leesang.mylocaldiary.admin.aggregate.SuspensionType;
import com.leesang.mylocaldiary.admin.dto.RequestReportDTO;
import com.leesang.mylocaldiary.admin.repository.ReportReasonRepository;
import com.leesang.mylocaldiary.admin.repository.ReportRepository;
import com.leesang.mylocaldiary.admin.repository.SuspensionRepository;
import com.leesang.mylocaldiary.common.exception.ErrorCode;
import com.leesang.mylocaldiary.common.exception.GlobalException;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberStatus;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.post.jpa.entity.Post;
import com.leesang.mylocaldiary.post.jpa.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCommandServiceImpl implements AdminCommandService {

	private final MemberRepository memberRepository;
	private final PostRepository postRepository;

	private final ReportRepository reportRepository;
	private final ReportReasonRepository reportReasonRepository;

	private final SuspensionRepository suspensionRepository;

	// 신고 처리 완료 -> 정지
	@Override
	@Transactional
	public void resolveReport(int reportId) {
		// 처리 상태 변경할 신고 내역 : request
		ReportEntity targetReport = reportRepository.findById(reportId)
			.orElseThrow(() -> new GlobalException(ErrorCode.REPORT_NOT_FOUND));

		// 처리 완료
		targetReport.setStatus(ReportStatus.RESOLVED);
		reportRepository.save(targetReport);

		// 신고 대상 회원 찾기
		String targetReportType = String.valueOf(targetReport.getReportedId());

		ReportType reportType = targetReport.getReportType();  // ← 올바른 타입 가져오기
		log.info("📌 ReportType = {}", reportType);

		MemberEntity targetMember = null;

		if (reportType == ReportType.MEMBER) {
			targetMember = memberRepository.findById(targetReport.getReportedId())
				.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

		} else if (reportType == ReportType.POST) {
			Post targetPost = postRepository.findById((long)targetReport.getReportedId())
				.orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));
			targetMember = targetPost.getMember();

		} else if (reportType == ReportType.COMMENT) {
			log.info("댓글 신고");
			// TODO: 댓글 엔티티 구현 시 적용
		}

		// 1. 신고 대상 회원 가져오기
		// MemberEntity targetMember = memberRepository.findById(request.getMemberId())
		// 	.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

		// 2. 신고 횟수 증가
		targetMember.setReportCount(targetMember.getReportCount() + 1);
		int updatedReportCount = targetMember.getReportCount();

		// 3. 신고 횟수에 따른 정지/탈퇴 처리 로직
		if (updatedReportCount == 1) {
			suspendMember(targetMember, 3); // 3일 정지
		} else if (updatedReportCount == 2) {
			if (MemberStatus.SUSPENDED.equals(targetMember.getStatus())) {
				// 이미 정지 상태면 기간 연장
				extendSuspension(targetMember, 30); // +30일 연장
			} else {
				suspendMember(targetMember, 30); // 새롭게 30일 정지
			}
		} else if (updatedReportCount >= 3) {
			// 영구 탈퇴 처리
			targetMember.setStatus(MemberStatus.DELETED);
			targetMember.setIsDeleted(true);
			targetMember.setSuspensionCount(targetMember.getSuspensionCount() + 1);
		}
		log.info("🚨 신고 처리 완료: reportId={}, type={}, targetId={}", reportId, targetReportType, targetMember.getId());
		memberRepository.save(targetMember);

	}

	// 신고 반려
	@Override
	public void rejectReport(int reportId) {
		// 처리 상태 변경할 신고 내역 : request
		ReportEntity targetReport = reportRepository.findById(reportId)
			.orElseThrow(() -> new GlobalException(ErrorCode.REPORT_NOT_FOUND));

		targetReport.setStatus(ReportStatus.REJECTED);
		reportRepository.save(targetReport);
		log.info("🚫 신고 반려 완료: reportId={}", reportId);
	}


	// 회원 정지
	private void suspendMember(MemberEntity member, int days) {
		member.setStatus(MemberStatus.SUSPENDED);
		member.setSuspensionCount(member.getSuspensionCount() + 1);

		SuspensionEntity suspension = SuspensionEntity.builder()
			.member(member)
			.suspensionStartDate(LocalDate.now().atStartOfDay())
			.suspensionEndDate(LocalDate.now().plusDays(days).atStartOfDay())
			.type(SuspensionType.valueOf("AUTO"))
			.build();

		suspensionRepository.save(suspension);
	}


	// 정지 기간 연장
	private void extendSuspension(MemberEntity targetMember, int additionalDays) {
		// 최신 정지 이력 가져오기
		SuspensionEntity latest = suspensionRepository.findTopByMemberOrderBySuspensionEndDateDesc(targetMember)
			.orElseThrow(() -> new GlobalException(ErrorCode.SUSPENSION_NOT_FOUND));

		latest.setSuspensionEndDate(latest.getSuspensionEndDate().plusDays(additionalDays));
		suspensionRepository.save(latest);

		targetMember.setSuspensionCount(targetMember.getSuspensionCount() + 1);
	}

	/**
	 * 매일 24시 정지 해제 스케줄러
	 */
	@Transactional
	@Scheduled(cron = "0 0 0 * * *") // 매일 00:00에 실행
	public void liftSuspensions() {
		log.info("🛠️ 정지 해제 스케줄러 실행됨");

		LocalDateTime now = LocalDateTime.now();

		List<SuspensionEntity> endedSuspensions = suspensionRepository.findBySuspensionEndDateBefore(now);

		for (SuspensionEntity suspension : endedSuspensions) {
			MemberEntity member = suspension.getMember();

			if (MemberStatus.SUSPENDED.equals(member.getStatus())) {
				member.setStatus(MemberStatus.ACTIVE);
				memberRepository.save(member);

				log.info("✅ 회원 ID={} 정지 해제 완료", member.getId());
			}
		}
	}


	// 회원 영구 탈퇴
	// 정지 기간 상관 없이 관리자가 임의로 강제 탈퇴시키는 경우
	@Override
	@Transactional
	public void banMember(Integer memberId) {
		// 1. 신고 대상 회원 가져오기
		MemberEntity targetMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

		// 2. 회원 상태 변경
		targetMember.setStatus(MemberStatus.BANNED);
		targetMember.setIsDeleted(true);

		memberRepository.save(targetMember);
	}

	// 신고 신청
	@Override
	@Transactional
	public void createReport(RequestReportDTO requestReportDTO) {
		// 신고 신청자
		MemberEntity reporter = memberRepository.findById(requestReportDTO.getMemberId())
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

		// 신고 사유
		ReportReasonEntity reportReason = reportReasonRepository.findById(requestReportDTO.getReportReasonId())
			.orElseThrow(() -> new GlobalException(ErrorCode.REPORT_REASON_NOT_FOUND));

		ReportEntity newReport = ReportEntity.builder()
			.reportType(ReportType.valueOf(requestReportDTO.getReportType())) // POST, COMMENT, MEMBER
			.reportedId(requestReportDTO.getReportedId())
			.content(requestReportDTO.getContent())
			.status(ReportStatus.WAITING) // 무조건 처음엔 처리중(WAITING) 상태
			.member(reporter) // 신고한 사람
			.reportReason(reportReason)		// 신고 사유
			.createdAt(LocalDateTime.now()) // 현재 시간
			.build();

		reportRepository.save(newReport);

	}



}

  // {
	//   "id": "1",
	//   "created_at": "2025.03.13",
	//   "report_type": "게시글",
	//   "reported_id": 1,
	//   "content": "이 게시글은 부적절한 내용을 포함하고 있습니다.",
	//   "status": "RESOLVED",
	//   "member_id": 3,
	//   "report_reason_id": "9"
	//   },
