package com.leesang.mylocaldiary.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.leesang.mylocaldiary.admin.dto.ReportResponseDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionResponseDTO;
import com.leesang.mylocaldiary.admin.mapper.ReportMapper;
import com.leesang.mylocaldiary.admin.mapper.SuspensionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminQueryServiceImpl implements AdminQueryService {

	private final ReportMapper reportMapper;
	private final SuspensionMapper suspensionMapper;

	//
	@Override
	public List<ReportResponseDTO> getReportList() {
		return reportMapper.selectReportList();
	}

	@Override
	public List<SuspensionResponseDTO> getSuspensionList() {
		return suspensionMapper.selectSuspensionList();
	}
}
