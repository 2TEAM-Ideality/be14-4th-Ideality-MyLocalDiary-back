package com.leesang.mylocaldiary.admin.service;

// import com.leesang.mylocaldiary.admin.dto.ReportRequestDTO;
import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.RequestReportDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionCreateRequestDTO;

import java.util.List;

public interface AdminCommandService {

	// 신고 처리 (승인/반려 등)
	void handleReport(RequestReportDTO request, String handleType);

	// 회원 영구 정지
	void banMember(Integer memberId);

}
