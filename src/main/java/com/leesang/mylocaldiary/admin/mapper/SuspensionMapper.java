package com.leesang.mylocaldiary.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leesang.mylocaldiary.admin.dto.SuspensionDTO;

@Mapper
public interface SuspensionMapper {
	List<SuspensionDTO> selectSuspensionList();
}
