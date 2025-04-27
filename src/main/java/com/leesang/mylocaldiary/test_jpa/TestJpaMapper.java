package com.leesang.mylocaldiary.test_jpa;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TestJpaMapper {

    @Update("UPDATE test SET test = #{value}")
    int updateFirstTest(@Param("value") String value);
}
