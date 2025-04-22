package com.leesang.mylocaldiary.test_mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestMapper testMapper;

    public String test() {
        return testMapper.test();
    }
}
