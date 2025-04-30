package com.leesang.mylocaldiary.follow.jpa.service;

import com.leesang.mylocaldiary.follow.jpa.entity.Follow;
import com.leesang.mylocaldiary.follow.jpa.repository.FollowRepository;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.notification.service.NotificationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final NotificationService notificationService; // ✅ 알림 처리
    private final MemberRepository memberRepository;       // ✅ 닉네임 조회용

    @Transactional
    public void follow(Long fromId, Long toId, Boolean status) {
        if (followRepository.existsByFollowingMemberIdAndFollowTargetMemberId(fromId, toId)) {
            return;
        }

        Follow follow = new Follow();
        follow.setFollowingMemberId(fromId);
        follow.setFollowTargetMemberId(toId);
        follow.setStatus(status);
        followRepository.save(follow);

        // ✅ 공개 여부 상관없이 알림 생성
        String nickname = memberRepository.findById(fromId.intValue())
                .map(MemberEntity::getNickname)
                .orElse("알 수 없음");

        notificationService.sendFollowNotification(toId, fromId, nickname);
    }


    @Transactional
    public void unfollow(Long fromId, Long toId) {
        Optional<Follow> follow = followRepository.findByFollowingMemberIdAndFollowTargetMemberId(fromId, toId);
        if (follow.isPresent()) {
            followRepository.delete(follow.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "팔로우 관계가 존재하지 않습니다.");
        }
    }

}
