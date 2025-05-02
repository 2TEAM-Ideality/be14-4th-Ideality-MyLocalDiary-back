package com.leesang.mylocaldiary.comment;

import com.leesang.mylocaldiary.comment.dto.CommentRequest;
import com.leesang.mylocaldiary.comment.entity.Comment;
import com.leesang.mylocaldiary.comment.repository.CommentRepository;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.post.jpa.entity.Post;
import com.leesang.mylocaldiary.post.jpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void createComment(CommentRequest req) {
        Post post = postRepository.findById(req.postId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        MemberEntity member = memberRepository.findById(req.memberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Comment.CommentBuilder builder = Comment.builder()
                .content(req.content())
                .post(post)
                .member(member);

        if (req.parentCommentId() != null) {
            Comment parent = commentRepository.findById(req.parentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid parent comment ID"));
            builder.parentComment(parent);
        }

        if (req.targetMemberId() != null) {
            MemberEntity target = memberRepository.findById(req.targetMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid target member ID"));
            builder.targetMember(target);
        }

        commentRepository.save(builder.build());
    }
}
