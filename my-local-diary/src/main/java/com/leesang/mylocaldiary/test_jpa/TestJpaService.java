package com.leesang.mylocaldiary.test_jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestJpaService {
    private final TestJpaMapper testMapper;

    public int updateFirstToJpa(String value) {
        return testMapper.updateFirstTest(value);
    }
}
