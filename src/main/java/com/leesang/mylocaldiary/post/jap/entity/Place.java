package com.leesang.mylocaldiary.post.jap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    private Integer orders;

    private String thumbnailImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Place(String name, BigDecimal latitude, BigDecimal longitude, Integer orders, String thumbnailImage) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.orders = orders;
        this.thumbnailImage = thumbnailImage;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}