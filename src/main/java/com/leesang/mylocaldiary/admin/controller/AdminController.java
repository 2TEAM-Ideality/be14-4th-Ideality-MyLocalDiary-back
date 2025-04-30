package com.leesang.mylocaldiary.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.ReportDetailDTO;
import com.leesang.mylocaldiary.admin.dto.RequestHandleReportDTO;
import com.leesang.mylocaldiary.admin.dto.RequestReportDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionDTO;
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

	private final AdminCommandService adminCommandService;  // 커맨드용
	private final AdminQueryService adminQueryService;		// 조회용

	// 신고 신청 
	@PostMapping("/report/create")
	public ResponseEntity<CommonResponseVO<?>> createReport(@RequestBody RequestReportDTO requestReportDTO) {
		log.info("신고 신청 들어옴");
		adminCommandService.createReport(requestReportDTO);

		CommonResponseVO<Object> res = CommonResponseVO.builder()
			.status(200)
			.message("신고 요청이 성공적으로 마무리되었습니다.")
			.data(null)
			.build();

		return ResponseEntity.ok(res);
	}

	// 신고 내역 전체 조회
	@GetMapping("/report/all")
	public ResponseEntity<CommonResponseVO<?>> getReports() {
		log.info("신고 내역 조회 요청 들어옴");
		List<ReportDTO> reportList =  adminQueryService.getReportList();

		CommonResponseVO<Object> res = CommonResponseVO.builder()
			.status(200)
			.message("신고 내역 조회 성공")
			.data(reportList)
			.build();

		return ResponseEntity.ok(res);
	}

	// 신고 내용 상세 조회
	@GetMapping("/report/{id}/detail")
	public ResponseEntity<CommonResponseVO<?>> getReportDetail(@PathVariable Integer reportId) {
		log.info("신고 번호 ID={} 에 대한 내역 상세 조회 요청", reportId);
		ReportDetailDTO reportDetail = adminQueryService.getReportDetail(reportId);
		CommonResponseVO<Object> res = CommonResponseVO.builder()
			.status(200)
			.message("규제 내역 조회 성공")
			.data(reportDetail)
			.build();
		return ResponseEntity.ok(res);
	}

	// 규제 내역 조회
	@GetMapping("/suspensions")
	public ResponseEntity<CommonResponseVO<?>> getSuspensions() {
		log.info("규제 내역 조회 요청 들어옴");
		List<SuspensionDTO> suspensionList = adminQueryService.getSuspensionList();
		CommonResponseVO<Object> res = CommonResponseVO.builder()
			.status(200)
			.message("규제 내역 조회 성공")
			.data(suspensionList)
			.build();
		return ResponseEntity.ok(res);
	}

	// 신고 처리 (처리 완료 -> 정지)
	@PatchMapping("/report/{reportId}/resolve")
	public ResponseEntity<CommonResponseVO<?>> resolveReport(@PathVariable int reportId) {
		log.info("신고 처리 요청: 신고 ID={}", reportId);
		adminCommandService.resolveReport(reportId);
		CommonResponseVO<Object> res = CommonResponseVO.builder()
			.status(200)
			.message("신고 내역 확인 후 정지 처리 완료")
			.data(null)
			.build();
		return ResponseEntity.ok(res);
	}

	// 신고 처리 (반려)
	@PatchMapping("/report/{reportId}/reject")
	public ResponseEntity<CommonResponseVO<?>> rejectReport(@PathVariable int reportId) {
		log.info("신고 처리 요청: 신고 ID={}", reportId);
		adminCommandService.rejectReport(reportId);
		
		CommonResponseVO<Object> res = CommonResponseVO.builder()
			.status(200)
			.message("신고 내역 확인 후 반려 처리 완료")
			.data(null)
			.build();
		return ResponseEntity.ok(res);
	}


	// 회원 강제 탈퇴
	@PostMapping("/ban/{memberId}")
	public ResponseEntity<CommonResponseVO<?>> handleBan(@PathVariable("memberId") int memberId) {
		log.info("회원 강제 탈퇴 요청: memberId={}", memberId);
		adminCommandService.banMember(memberId);

		CommonResponseVO<Object> res = CommonResponseVO.builder()
			.status(200)
			.message("관리자 권한 회원 강제 탈퇴 처리 완료")
			.data(null)
			.build();
		return ResponseEntity.ok(res);
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