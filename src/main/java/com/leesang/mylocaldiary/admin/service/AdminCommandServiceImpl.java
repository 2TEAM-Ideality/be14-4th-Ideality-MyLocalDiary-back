package com.leesang.mylocaldiary.admin.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leesang.mylocaldiary.admin.aggregate.ReportEntity;
import com.leesang.mylocaldiary.admin.dto.ReportResponseDTO;
import com.leesang.mylocaldiary.admin.dto.SuspensionCreateRequestDTO;
import com.leesang.mylocaldiary.admin.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminCommandServiceImpl implements AdminCommandService {

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
	@Transactional
	public void suspendMember(SuspensionCreateRequestDTO request) {
		// Member member = memberRepository.findById(request.getMemberId())
		// 	.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		//
		// Suspension suspension = new Suspension();
		// suspension.setMember(member);
		// suspension.setSuspensionStartDate(request.getSuspensionStartDate());
		// suspension.setSuspensionEndDate(request.getSuspensionEndDate());
		// suspension.setType(request.getType());
		//
		// suspensionRepository.save(suspension);
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
