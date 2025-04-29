package com.leesang.mylocaldiary.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leesang.mylocaldiary.admin.dto.ReportResponseDTO;

@Mapper
public interface ReportMapper {
	List<ReportResponseDTO> selectReportList();
}
