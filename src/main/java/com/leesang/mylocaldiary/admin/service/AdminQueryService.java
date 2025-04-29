package com.leesang.mylocaldiary.admin.service;

import java.util.List;

import com.leesang.mylocaldiary.admin.dto.ReportResponseDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionResponseDTO;

public interface AdminQueryService {
	List<ReportResponseDTO> getReportList();

	List<SuspensionResponseDTO> getSuspensionList();
}
