package com.leesang.mylocaldiary.post.jap.entity;

import com.leesang.mylocaldiary.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Lob
    private String diary;

    @Lob
    private String post; // (설명용, 썸네일? 요약글?)

    private String createdAt;
    private String updatedAt;

    private Integer likesCount = 0;

    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // (추후 Member entity 연결 필요)

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places = new ArrayList<>();

    @Builder
    public Post(String title, String diary, String post, String createdAt, String updatedAt, Member member) {
        this.title = title;
        this.diary = diary;
        this.post = post;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.member = member;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setPost(this);
    }

    public void addPlace(Place place) {
        places.add(place);
        place.setPost(this);
    }
}