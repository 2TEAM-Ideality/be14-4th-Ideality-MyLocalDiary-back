package com.leesang.mylocaldiary.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.leesang.mylocaldiary.admin.dto.ReportDTO;
import com.leesang.mylocaldiary.admin.dto.ReportDetailDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionDTO;
import com.leesang.mylocaldiary.admin.mapper.ReportMapper;
import com.leesang.mylocaldiary.admin.mapper.SuspensionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminQueryServiceImpl implements AdminQueryService {

	private final ReportMapper reportMapper;
	private final SuspensionMapper suspensionMapper;

	// 신고 내역 목록 조회
	@Override
	public List<ReportDTO> getReportList() {
		return reportMapper.selectReportList();
	}

	// 규제 내역 목록 조회
	@Override
	public List<SuspensionDTO> getSuspensionList() {
		return suspensionMapper.selectSuspensionList();
	}

	// 신고 내용 상세 조회
	@Override
	public ReportDetailDTO getReportDetail(Integer reportId) {
		return null;
	}
}
