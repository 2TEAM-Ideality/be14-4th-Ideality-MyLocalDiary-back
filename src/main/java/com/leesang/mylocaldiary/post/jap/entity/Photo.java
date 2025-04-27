package com.leesang.mylocaldiary.post.jap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageUrl;

    private Integer orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Photo(String imageUrl, Integer orders) {
        this.imageUrl = imageUrl;
        this.orders = orders;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}