package com.leesang.mylocaldiary.admin.service;

import java.util.List;

import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionResponseDTO;

public interface AdminQueryService {

	// 신고 내역 목록 조회
	List<ReportDTO> getReportList();

	// 처리중 신고 내역 조회
	List<ReportDTO> getWaitingReportList();

	// 규제 내역 목록 조회
	List<SuspensionResponseDTO> getSuspensionList();

}
