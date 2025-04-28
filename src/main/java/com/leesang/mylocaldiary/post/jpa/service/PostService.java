package com.leesang.mylocaldiary.post.jpa.service;

import com.leesang.mylocaldiary.member.entity.Member;
import com.leesang.mylocaldiary.post.jpa.dto.PostCreateRequest;
import com.leesang.mylocaldiary.post.jpa.entity.Photo;
import com.leesang.mylocaldiary.post.jpa.entity.Place;
import com.leesang.mylocaldiary.post.jpa.entity.Post;
import com.leesang.mylocaldiary.post.jpa.repository.PhotoRepository;
import com.leesang.mylocaldiary.post.jpa.repository.PlaceRepository;
import com.leesang.mylocaldiary.post.jpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final PlaceRepository placeRepository;
    private final S3Uploader s3Uploader;

    public Long createPost(PostCreateRequest request, List<MultipartFile> images, Member member) {
        Post post = Post.builder()
                .title(request.getTitle())
                .post(request.getPost())
                .diary(request.getDiary())
                .createdAt(now())
                .updatedAt(now())
                .member(member)
                .build();

        // 1. 사진 업로드 및 저장
        int photoOrder = 0;
        for (MultipartFile image : images) {
            String imageUrl = s3Uploader.upload(image, "post-images");
            Photo photo = Photo.builder()
                    .imageUrl(imageUrl)
                    .orders(photoOrder++)
                    .post(post)
                    .build();
            post.addPhoto(photo);
        }

        // 2. 장소 저장
        int placeOrder = 0;
        for (PostCreateRequest.PlaceDto placeDto : request.getPlaces()) {
            Place place = Place.builder()
                    .name(placeDto.getName())
                    .latitude(BigDecimal.valueOf(placeDto.getLatitude()))
                    .longitude(BigDecimal.valueOf(placeDto.getLongitude()))
                    .orders(placeOrder++)
                    .thumbnailImage(placeDto.getThumbnailImage())
                    .post(post)
                    .build();
            post.addPlace(place);
        }

        // 3. 저장
        postRepository.save(post);
        return post.getId();
    }

    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}