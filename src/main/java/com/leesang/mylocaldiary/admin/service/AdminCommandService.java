package com.leesang.mylocaldiary.admin.service;

// import com.leesang.mylocaldiary.admin.dto.ReportRequestDTO;
import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.RequestHandleReportDTO;

public interface AdminCommandService {

	// 신고 처리 (승인/반려 등)
	void handleReport(RequestHandleReportDTO request, String handleType);

	// 회원 영구 정지
	void banMember(Integer memberId);

	// 신고 신청
	void createReport(ReportDTO requestReportDTO);
}
