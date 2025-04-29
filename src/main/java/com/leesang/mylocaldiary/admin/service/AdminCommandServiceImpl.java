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
import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.RequestHandleReportDTO;
import com.leesang.mylocaldiary.admin.dto.RequestReportDTO;
import com.leesang.mylocaldiary.admin.repository.ReportReasonRepository;
import com.leesang.mylocaldiary.admin.repository.ReportRepository;
import com.leesang.mylocaldiary.admin.repository.SuspensionRepository;
import com.leesang.mylocaldiary.common.exception.ErrorCode;
import com.leesang.mylocaldiary.common.exception.GlobalException;
import com.leesang.mylocaldiary.member.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCommandServiceImpl implements AdminCommandService {

	private final MemberRepository memberRepository;

	private final ReportRepository reportRepository;
	private final ReportReasonRepository reportReasonRepository;

	private final SuspensionRepository suspensionRepository;

	// 신고 상태 처리 (처리 완료, 보류)
	@Override
	@Transactional
	public void handleReport(RequestHandleReportDTO request, String handleType) {
		// 처리 상태 변경할 신고 내역 : request
		ReportEntity targetReport = reportRepository.findById(request.getId())
			.orElseThrow(() -> new GlobalException(ErrorCode.REPORT_NOT_FOUND));

		if(handleType.equals("reject")){
			// 반려 처리
			targetReport.setStatus(ReportStatus.REJECTED);
			reportRepository.save(targetReport);
			return ;
		}
		// 처리 완료
		targetReport.setStatus(ReportStatus.RESOLVED);
		reportRepository.save(targetReport);

		// 1. 신고 대상 회원 가져오기
		MemberEntity targetMember = memberRepository.findById(request.getMemberId())
			.orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

		// 2. 신고 횟수 증가
		targetMember.setReportCount(targetMember.getReportCount() + 1);
		int updatedReportCount = targetMember.getReportCount();

		// 3. 신고 횟수에 따른 정지/탈퇴 처리 로직
		if (updatedReportCount == 1) {
			suspendMember(targetMember, 3); // 3일 정지
		} else if (updatedReportCount == 2) {
			if ("SUSPENDED".equals(targetMember.getStatus())) {
				// 이미 정지 상태면 기간 연장
				extendSuspension(targetMember, 30); // +30일 연장
			} else {
				suspendMember(targetMember, 30); // 새롭게 30일 정지
			}
		} else if (updatedReportCount >= 3) {
			// 영구 탈퇴 처리
			targetMember.setStatus("DELETED");
			targetMember.setIsDeleted(true);
			targetMember.setSuspensionCount(targetMember.getSuspensionCount() + 1);
		}
		memberRepository.save(targetMember);
		/*
		신고 들어옴 -> 신고 처리 (수동) -> 처리 완료 시
		1. 일단 신고 횟수 증가  member.report_count -> ++
		2. 신고 횟수가
			cnt == 1 -> 3일 정지
			cnt == 2 -> 30일 정지
			이미 정지 상태라면 ?
			정지 기간을 늘리기
			member.status -> SUSPENDED  정지로 바꾸기
			member.suspension_count -> ++  정지 횟수 올리기

			cnt == 3 -> -> 영구 탈퇴
			member.suspension_count -> ++  정지 횟수 올리기
			member.status -> DELETED
	 */
	}


	// 회원 정지
	private void suspendMember(MemberEntity member, int days) {
		member.setStatus("SUSPENDED");
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

			if ("SUSPENDED".equals(member.getStatus())) {
				member.setStatus("ACTIVE");
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
		targetMember.setStatus("BANNED");
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
