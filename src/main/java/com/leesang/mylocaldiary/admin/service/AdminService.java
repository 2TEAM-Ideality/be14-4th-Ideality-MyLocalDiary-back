package com.leesang.mylocaldiary.admin.service;

// import com.leesang.mylocaldiary.admin.dto.ReportRequestDTO;
import com.leesang.mylocaldiary.admin.dto.ReportResponseDTO;
import com.leesang.mylocaldiary.admin.repository.ReportRepository;

import java.util.List;

public interface AdminService {

	// 신고 목록 조회
	List<ReportResponseDTO> getReportList();

	// 신고 상세 조회
	ReportResponseDTO getReportDetail(Integer reportId);

	// 신고 처리 (승인/반려 등)
	void handleReport(Integer reportId, boolean isApprove);

	// 회원 정지
	void suspendMember(Integer memberId, int days);

	// 회원 영구 정지
	void banMember(Integer memberId);
}
