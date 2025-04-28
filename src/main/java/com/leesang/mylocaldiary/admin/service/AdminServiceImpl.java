package com.leesang.mylocaldiary.admin.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.leesang.mylocaldiary.admin.aggregate.ReportEntity;
import com.leesang.mylocaldiary.admin.dto.ReportResponseDTO;
import com.leesang.mylocaldiary.admin.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final ReportRepository reportRepository;

	// 신고 목록 조회
	@Override
	public List<ReportResponseDTO> getReportList() {
		return reportRepository.findAll().stream()
			.map(this::convertToResponseDTO)
			.toList();
	}



	@Override
	public ReportResponseDTO getReportDetail(Integer reportId) {
		return null;
	}

	@Override
	public void handleReport(Integer reportId, boolean isApprove) {

	}

	@Override
	public void suspendMember(Integer memberId, int days) {

	}

	@Override
	public void banMember(Integer memberId) {

	}


	private ReportResponseDTO convertToResponseDTO(ReportEntity report) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

		return ReportResponseDTO.builder()
			.id(report.getId())
			.createdAt(report.getCreatedAt().format(formatter))
			.reportType(report.getReportType().getDescription())
			.reportedId(report.getReportedId())
			.content(report.getContent())
			.status(report.getStatus().getDescription())
			.memberId(report.getMember().getId())
			.reportReasonId(String.valueOf(report.getReportReason().getId()))
			.build();
	}
}
