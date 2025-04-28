package com.leesang.mylocaldiary.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leesang.mylocaldiary.admin.service.AdminService;
import com.leesang.mylocaldiary.common.response.CommonResponseVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private CommonResponseVO commonResponseVO;
	private final AdminService adminService;


	// TODO. 신고 내역 조회
	@GetMapping("/reports")
	public CommonResponseVO getReports() {
		return CommonResponseVO.success(
			"신고 목록 조회 성공",
			adminService.getReportList()
		);
	}

	// TODO. 규제 내역 조회

	// TODO. 규제 상태 변경

	// TODO. 신고 처리 상태 변경



}

// {
// 	"status": 200,
// 	"message": "로그인 성공",
// 	"data": {
// 	"accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWRIjoib3VyLXNvcHQbpXkxZgFXHw"
// 	}
// 	}