package com.leesang.mylocaldiary.follow.jpa.service;

import com.leesang.mylocaldiary.follow.jpa.entity.Follow;
import com.leesang.mylocaldiary.follow.jpa.repository.FollowRepository;
import com.leesang.mylocaldiary.notification.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    @Transactional
    public void follow(Long fromId, Long toId, boolean status) {
        if (followRepository.findByFollowingMemberIdAndFollowTargetMemberId(fromId, toId).isPresent()) {
            throw new IllegalStateException("이미 팔로우함");
        }

        Follow follow = new Follow();
        follow.setFollowingMemberId(fromId);
        follow.setFollowTargetMemberId(toId);
        follow.setStatus(status);

        followRepository.save(follow);
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
