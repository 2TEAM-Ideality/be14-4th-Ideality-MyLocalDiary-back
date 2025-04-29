package com.leesang.mylocaldiary.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leesang.mylocaldiary.admin.dto.ReportDTO;

@Mapper
public interface ReportMapper {
	List<ReportDTO> selectReportList();

	List<ReportDTO> selectWaitingReportList();
}
