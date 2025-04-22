package com.leesang.mylocaldiary;

import com.leesang.mylocaldiary.test_jpa.TestJpaService;
import com.leesang.mylocaldiary.test_mybatis.TestService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testMybatisService;
    private final TestJpaService testJpaService;

    @GetMapping("/")
    @ResponseBody
    public String test(){
        return testMybatisService.test();
    }

    @PutMapping("/update")
    @ResponseBody
    public String updateTestValue(@RequestBody Map<String, String> body) {
        String value=body.get("value");
        int updated=testJpaService.updateFirstToJpa(value);
        if(updated>0){return "success. Click Mybatis Test Button";}
        else{return "fail.";}
    }
}
