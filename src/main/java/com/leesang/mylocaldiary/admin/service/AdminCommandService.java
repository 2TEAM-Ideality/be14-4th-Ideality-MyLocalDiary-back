package com.leesang.mylocaldiary.admin.service;

// import com.leesang.mylocaldiary.admin.dto.ReportRequestDTO;
import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.RequestHandleReportDTO;
import com.leesang.mylocaldiary.admin.dto.RequestReportDTO;

public interface AdminCommandService {
	// 신고 신청
	void createReport(RequestReportDTO requestReportDTO);

	// 신고 처리 완료
	void resolveReport(int reportId);

	// 신고 반려
	void rejectReport(int reportId);
	// 회원 영구 정지
	void banMember(Integer memberId);


}
