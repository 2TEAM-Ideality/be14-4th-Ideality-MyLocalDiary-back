package com.leesang.mylocaldiary.mypagedetail.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageFollowMapper {
    int countFollowing(@Param("memberId") int memberId);
    List<FollowedUserDTO> getFollowingList(@Param("memberId") int memberId);
}