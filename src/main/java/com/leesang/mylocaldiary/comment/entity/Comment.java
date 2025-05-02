package com.leesang.mylocaldiary.comment.entity;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.post.jpa.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String content;

    private String createdAt;

    private String updatedAt;

    @Column(name = "likes_count", nullable = false)
    private Integer likesCount;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    // 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id")
    private MemberEntity targetMember;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now().toString();
        this.updatedAt = this.createdAt;
        this.likesCount = 0;
        this.isDeleted = false;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now().toString();
    }
}
