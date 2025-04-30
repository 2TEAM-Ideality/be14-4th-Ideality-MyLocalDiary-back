package com.leesang.mylocaldiary.s3;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    private final String bucketName = "my-local-diary-prod"; // 버킷 이름

    // 테스트용

    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        String fileName = "profile.webp"; // 🔥 고정 파일명
        String filePath = folderName + "/" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(filePath)
            .contentType(file.getContentType()) // 🔥 여기서 Content-Type 직접 설정
            .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return filePath;
    }



}
