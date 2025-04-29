package com.leesang.mylocaldiary.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


	// TODO. 신고 내역 전체 조회
	@GetMapping("/reports")
	public CommonResponseVO getReports() {
		log.info("신고 내역 조회 요청 들어옴");
		return CommonResponseVO.success(
			"신고 목록 조회 성공",
			adminQueryService.getReportList()
		);
	}

	// TODO. 규제 내역 조회
	@GetMapping("/suspensions")
	public CommonResponseVO getSuspensions() {
		log.info("규제 내역 조회 요청 들어옴");
		return CommonResponseVO.success(
			"규제 내역 목록 조회 성공",
			adminQueryService.getSuspensionList()
		);
	}

	// TODO. 규제 상태 (정지중 -> 해체됨) 변경
	// @PatchMapping("/suspensions/{id}")
	// public CommonResponseVO updateSuspensionStatus(
	// 	@PathVariable Long id,
	// 	@RequestBody SuspensionUpdateRequestDTO request
	// ) {
	// 	log.info("규제 상태 변경 요청 들어옴, id = {}, status = {}", id, request.getStatus());
	//
	// 	adminCommandService.updateSuspensionStatus(id, request.getStatus());
	//
	// 	return CommonResponseVO.success(
	// 		"규제 상태 변경 성공",
	// 		null
	// 	);
	// }


	// TODO. 신고 처리 상태 변경 - 회원 정지
	@PatchMapping("/suspend")
	public CommonResponseVO suspendMember(@RequestBody SuspensionCreateRequestDTO request) {
		log.info("회원 정지 요청 들어옴, memberId = {}", request.getMemberId());

		adminCommandService.suspendMember(request);

		return CommonResponseVO.success(
			"회원 정지 성공",
			null
		);
	}





}

// {
// 	"status": 200,
// 	"message": "로그인 성공",
// 	"data": {
// 	"accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWRIjoib3VyLXNvcHQbpXkxZgFXHw"
// 	}
// 	}