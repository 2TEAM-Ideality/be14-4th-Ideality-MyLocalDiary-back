package com.leesang.mylocaldiary.admin.service;

import java.util.List;

import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.ReportDetailDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionDTO;

public interface AdminQueryService {

	// 신고 내역 목록 조회
	List<ReportDTO> getReportList();

	// 규제 내역 목록 조회
	List<SuspensionDTO> getSuspensionList();

	// 신고 내용 상세 조회
	ReportDetailDTO getReportDetail(Integer reportId);
}
