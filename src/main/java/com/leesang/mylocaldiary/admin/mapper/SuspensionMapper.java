package com.leesang.mylocaldiary.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leesang.mylocaldiary.admin.dto.SuspensionResponseDTO;

@Mapper
public interface SuspensionMapper {
	List<SuspensionResponseDTO> selectSuspensionList();
}
