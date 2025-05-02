package com.leesang.mylocaldiary.post.mybatis.controller;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import com.leesang.mylocaldiary.post.mybatis.dto.CommentResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.MyPostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostDateResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostSimpleResponse;
import com.leesang.mylocaldiary.post.mybatis.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostQueryController {

    private final PostQueryService postQueryService;

    // 1. 내가 쓴 게시글 전체 조회 (지도/마이페이지)
    @GetMapping("/my/map")
    public ResponseEntity<List<PostSimpleResponse>> findMyPostsForMap(@RequestParam Integer memberId) {
        return ResponseEntity.ok(postQueryService.findMyPostsForMap(memberId));
    }

    // 1-1. 내가 쓴 게시글 전제 조회 (마이페이지/캘린더)
    @GetMapping("/calendar")
    public ResponseEntity<CommonResponseVO<List<PostDateResponse>>> findMyPostsForCalendar(@RequestParam Integer memberId) {
        List<PostDateResponse> data=postQueryService.findMyPostsForCalendar(memberId);
        CommonResponseVO<List<PostDateResponse>> response=CommonResponseVO.<List<PostDateResponse>>builder()
                .status(200)
                .message("조회 성공")
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    // 2. 내가 쓴 게시글 상세 조회
    @GetMapping("/my/{postId}")
    public ResponseEntity<MyPostDetailResponse> findMyPostDetail(@PathVariable Integer postId, @RequestParam Integer memberId) {
        return ResponseEntity.ok(postQueryService.findMyPostDetail(postId, memberId));
    }

    // 3. 팔로우한 유저 게시글 전체 조회 (지도용)
    @GetMapping("/follow/map")
    public ResponseEntity<List<PostSimpleResponse>> findFollowedPostsForMap(@RequestParam Integer memberId) {
        return ResponseEntity.ok(postQueryService.findFollowedPostsForMap(memberId));
    }

    // 4. 팔로우한 유저 게시글 상세 조회
    @GetMapping("/follow/{postId}")
    public ResponseEntity<PostDetailResponse> findFollowedPostDetail(@PathVariable Integer postId, @RequestParam Integer memberId) {
        return ResponseEntity.ok(postQueryService.findFollowedPostDetail(postId, memberId));
    }

    // 5. 게시글 좋아요 수 조회
    @GetMapping("/like/count")
    public ResponseEntity<Integer> countPostLikes(@RequestParam Integer postId) {
        return ResponseEntity.ok(postQueryService.countPostLikes(postId));
    }

    // 6. 댓글 좋아요 수 조회
    @GetMapping("/comment/like/count")
    public ResponseEntity<Integer> countCommentLikes(@RequestParam Integer commentId) {
        return ResponseEntity.ok(postQueryService.countCommentLikes(commentId));
    }

    // 7. 게시글 좋아요 여부 조회
    @GetMapping("/like/check")
    public ResponseEntity<Boolean> isPostLikedByCurrentUser(@RequestParam Integer postId, @RequestParam Integer memberId) {
        return ResponseEntity.ok(postQueryService.isPostLikedByCurrentUser(postId, memberId));
    }

    // 8. 댓글 조회 (최상위 부모 댓글)
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponse>> findCommentsByPostId(@RequestParam Integer postId) {
        return ResponseEntity.ok(postQueryService.findCommentsByPostId(postId));
    }

    // 9. 대댓글 조회 (부모댓글에 연결된)
    @GetMapping("/replies")
    public ResponseEntity<List<CommentResponse>> findRepliesByParentCommentId(@RequestParam Integer parentCommentId) {
        return ResponseEntity.ok(postQueryService.findRepliesByParentCommentId(parentCommentId));
    }

    // 10. 댓글 좋아요 여부 조회
    @GetMapping("/comment/like/check")
    public ResponseEntity<Boolean> isCommentLikedByCurrentUser(
            @RequestParam Integer commentId,
            @RequestParam Integer memberId
    ) {
        return ResponseEntity.ok(postQueryService.isCommentLikedByCurrentUser(commentId, memberId));
    }

}