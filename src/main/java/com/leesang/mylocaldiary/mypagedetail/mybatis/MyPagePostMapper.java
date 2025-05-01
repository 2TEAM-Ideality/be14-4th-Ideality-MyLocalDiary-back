package com.leesang.mylocaldiary.mypagedetail.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyPagePostMapper {
    int countPostsByMemberId(@Param("memberId") Long memberId);
}