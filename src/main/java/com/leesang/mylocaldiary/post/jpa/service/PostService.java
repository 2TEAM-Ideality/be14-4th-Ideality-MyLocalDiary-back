package com.leesang.mylocaldiary.post.jpa.service;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
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

    public Long createPost(PostCreateRequest request, List<MultipartFile> images, List<MultipartFile> thumbnails, MemberEntity member) {
        // 1. 게시글 생성 및 저장 (postId 확보용)
        Post post = Post.builder()
            .title(request.getTitle())
            .post(request.getPost())
            .diary(request.getDiary())
            .createdAt(now())
            .updatedAt(now())
            .member(member)
            .build();

        postRepository.save(post); // 먼저 저장 → post.getId() 사용 가능

        // 2. 게시글 사진 업로드
        int photoOrder = 0;
        for (MultipartFile image : images) {
            String folderPath = "post/" + post.getId() + "/images";
            String imageUrl = s3Uploader.upload(image, folderPath);
            Photo photo = Photo.builder()
                .imageUrl(imageUrl)
                .orders(photoOrder++)
                .post(post)
                .build();
            post.addPhoto(photo);
        }

        // 3. 장소 + 썸네일 업로드
        int placeOrder = 0;
        for (int i = 0; i < request.getPlaces().size(); i++) {
            PostCreateRequest.PlaceDto placeDto = request.getPlaces().get(i);
            MultipartFile thumbnail = thumbnails.get(i);

            String thumbnailPath = "post/" + post.getId() + "/thumbnails";
            String thumbnailUrl = s3Uploader.upload(thumbnail, thumbnailPath);

            Place place = Place.builder()
                .name(placeDto.getName())
                .latitude(BigDecimal.valueOf(placeDto.getLatitude()))
                .longitude(BigDecimal.valueOf(placeDto.getLongitude()))
                .orders(placeOrder++)
                .thumbnailImage(thumbnailUrl)
                .post(post)
                .build();
            post.addPlace(place);
        }

        return post.getId();
    }

    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}