package com.leesang.mylocaldiary.post.jap.entity;

import com.leesang.mylocaldiary.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String post;

    @Column(columnDefinition = "TEXT")
    private String diary;

    private String createdAt;
    private String updatedAt;

    private Integer likesCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places = new ArrayList<>();

    @Builder
    public Post(String title, String post, String diary, String createdAt, String updatedAt, Member member) {
        this.title = title;
        this.post = post;
        this.diary = diary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likesCount = 0;
        this.member = member;
        this.photos = new ArrayList<>();
        this.places = new ArrayList<>();
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