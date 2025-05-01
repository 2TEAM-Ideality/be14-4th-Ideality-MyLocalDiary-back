package com.leesang.mylocaldiary.mypagedetail.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MyPagePostMapper {

    @Select("SELECT COUNT(*) FROM post WHERE member_id = #{memberId} AND is_deleted = FALSE")
    int countPostsByMemberId(Long memberId);
}
