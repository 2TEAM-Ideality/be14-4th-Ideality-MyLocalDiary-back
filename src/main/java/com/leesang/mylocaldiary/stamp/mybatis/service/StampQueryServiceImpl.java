package com.leesang.mylocaldiary.stamp.mybatis.service;

import com.leesang.mylocaldiary.stamp.mybatis.mapper.StampMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StampQueryServiceImpl implements StampQueryService {

    private final StampMapper stampMapper;

    @Autowired
    public StampQueryServiceImpl(StampMapper stampMapper) {
        this.stampMapper = stampMapper;
    }

    @Override
    public Map<String, Integer> findMemberStamp(Integer memberId) {
        List<Map<String, Object>> result = stampMapper.selectMemberStamp(memberId);
        return result.stream().collect(Collectors.toMap(
                e -> (String) e.get("stampName"),
                e -> ((Long) e.get("cnt")).intValue()
        ));
    }

    @Override
    public List<String> findBadgeByMemberId(Integer memberId) {
        return stampMapper.selectBadgeByMemberId(memberId);
    }
}
