package com.leesang.mylocaldiary.post.jap.service;

import com.leesang.mylocaldiary.post.jap.dto.PostCreateRequest;
import com.leesang.mylocaldiary.post.jap.dto.PostResponse;
import com.leesang.mylocaldiary.post.jap.entity.Photo;
import com.leesang.mylocaldiary.post.jap.entity.Place;
import com.leesang.mylocaldiary.post.jap.entity.Post;
import com.leesang.mylocaldiary.post.jap.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostResponse savePost(PostCreateRequest request) {
        // 1. Post Entity 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .diary(request.getDiary())
                .post(request.getPost())
                .createdAt(nowString())
                .memberId(request.getMemberId())
                .isDeleted(false)
                .likesCount(0)
                .build();

        // 2. Photo Entity 리스트 생성
        List<Photo> photos = request.getPhotos().stream()
                .map(photoReq -> Photo.builder()
                        .imageUrl(photoReq.getImageUrl())
                        .orders(photoReq.getOrders())
                        .post(post) // 양방향 매핑
                        .build())
                .collect(Collectors.toList());

        // 3. Place Entity 리스트 생성
        List<Place> places = request.getPlaces().stream()
                .map(placeReq -> Place.builder()
                        .name(placeReq.getName())
                        .latitude(placeReq.getLatitude())
                        .longitude(placeReq.getLongitude())
                        .orders(placeReq.getOrders())
                        .thumbnailImage(placeReq.getThumbnailImage())
                        .post(post) // 양방향 매핑
                        .build())
                .collect(Collectors.toList());

        // 4. Post에 사진/장소 세팅
        post.setPhotos(photos);
        post.setPlaces(places);

        // 5. 저장
        Post savedPost = postRepository.save(post);

        // 6. 응답 변환
        return toPostResponse(savedPost);
    }

    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .diary(post.getDiary())
                .post(post.getPost())
                .createdAt(post.getCreatedAt())
                .photos(post.getPhotos().stream()
                        .map(photo -> PostResponse.PhotoDto.builder()
                                .id(photo.getId())
                                .imageUrl(photo.getImageUrl())
                                .orders(photo.getOrders())
                                .build())
                        .collect(Collectors.toList()))
                .places(post.getPlaces().stream()
                        .map(place -> PostResponse.PlaceDto.builder()
                                .id(place.getId())
                                .name(place.getName())
                                .latitude(place.getLatitude())
                                .longitude(place.getLongitude())
                                .orders(place.getOrders())
                                .thumbnailImage(place.getThumbnailImage())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private String nowString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}