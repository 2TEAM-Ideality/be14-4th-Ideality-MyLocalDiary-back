package com.leesang.mylocaldiary;

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
