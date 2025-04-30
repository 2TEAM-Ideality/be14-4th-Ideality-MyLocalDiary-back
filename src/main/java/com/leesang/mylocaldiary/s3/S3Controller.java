package com.leesang.mylocaldiary.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    // 프로필 이미지 업로드 (테스트용)
    @PostMapping("/upload/member-profile")
    public String uploadMemberProfile(@RequestParam("file") MultipartFile file,
        @RequestParam("userId") Long userId) throws Exception {
        String folderName = "member/" + userId + "/profile";
        return s3Service.uploadFile(file, folderName);
    }
}

/*
    흐름
    1. 프론트(Vue)에서 /api/s3/upload로 파일 POST
    2. Spring Boot가 파일 받아서 S3Service 통해 S3 업로드
    3. 업로드한 S3 Key(경로/파일명) 반환
*/

//
// 프로필 이미지 업로드	/api/s3/upload/member-profile