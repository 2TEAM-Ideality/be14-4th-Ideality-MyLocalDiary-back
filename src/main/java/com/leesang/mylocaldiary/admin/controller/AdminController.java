package com.leesang.mylocaldiary.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.RequestReportDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionCreateRequestDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionUpdateRequestDTO;
import com.leesang.mylocaldiary.admin.service.AdminCommandService;
import com.leesang.mylocaldiary.admin.service.AdminQueryService;
import com.leesang.mylocaldiary.common.response.CommonResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

	private CommonResponseVO commonResponseVO;
	private final AdminCommandService adminCommandService;  // 커맨드용
	private final AdminQueryService adminQueryService;		// 조회용


	// 신고 내역 전체 조회
	@GetMapping("/reports")
	public CommonResponseVO getReports() {
		log.info("신고 내역 조회 요청 들어옴");
		return CommonResponseVO.success(
			"신고 목록 조회 성공",
			adminQueryService.getReportList()
		);
	}
	// 처리중인 신고 내역 조회
	@GetMapping("/reports/waiting")
	public CommonResponseVO getWaitingReports() {
		log.info("처리중인 신고 내역 조회 요청");
		return CommonResponseVO.success(
			"처리중 신고 내역 목록 조회 성공",
			adminQueryService.getWaitingReportList()
		);
	}

	// 규제 내역 조회
	@GetMapping("/suspensions")
	public CommonResponseVO getSuspensions() {
		log.info("규제 내역 조회 요청 들어옴");
		return CommonResponseVO.success(
			"규제 내역 목록 조회 성공",
			adminQueryService.getSuspensionList()
		);
	}

	// 신고 처리 (처리 완료)
	@PatchMapping("/resolve/report")
	public CommonResponseVO resolveReport(@RequestBody RequestReportDTO request) {
		log.info("신고 처리 요청: 신고 ID={}", request.getMemberId());
		adminCommandService.handleReport(request, "resolve");
		return CommonResponseVO.success(
			"신고 처리 완료"
		);
	}

	// 신고 처리 반려
	@PatchMapping("/reject/report")
	public CommonResponseVO rejectReport(@RequestBody RequestReportDTO request) {
		log.info("신고 처리 요청: 신고 ID={}", request.getMemberId());
		adminCommandService.handleReport(request, "reject");
		return CommonResponseVO.success(
			"신고 반려"
		);
	}


	// 회원 강제 탈퇴
	@PostMapping("/ban/{memberId}")
	public CommonResponseVO handleBan(@PathVariable("memberId") int memberId) {
		log.info("회원 강제 탈퇴 요청: memberId={}", memberId);
		adminCommandService.banMember(memberId);
		return CommonResponseVO.success(
			"회원 강제 탈퇴 완료"
		);
	}

	// TODO. 서비스 정보 조회 (대시보드용)


	// 	회원 수
	// 1200
	// 	게시글 수
	// 3400
	// 	신고 건수
	// 45
	// 	활성 회원 수
	// 750






}

// {
// 	"status": 200,
// 	"message": "로그인 성공",
// 	"data": {
// 	"accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWRIjoib3VyLXNvcHQbpXkxZgFXHw"
// 	}
// 	}