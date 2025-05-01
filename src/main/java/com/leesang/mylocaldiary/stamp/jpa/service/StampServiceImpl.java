package com.leesang.mylocaldiary.stamp.jpa.service;

import com.leesang.mylocaldiary.stamp.jpa.entity.MemberBadge;
import com.leesang.mylocaldiary.stamp.jpa.entity.MemberStamp;
import com.leesang.mylocaldiary.stamp.jpa.repository.MemberBadgeRepository;
import com.leesang.mylocaldiary.stamp.jpa.repository.MemberStampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class StampServiceImpl implements StampService{

    private final MemberStampRepository memberStampRepository;
    private final MemberBadgeRepository memberBadgeRepository;

    @Override
    public void registStamp(Long targetMemberId, Long stampId) {
        MemberStamp memberStamp = new MemberStamp();
        memberStamp.setMemberId(targetMemberId);
        memberStamp.setStampId(stampId);
        memberStamp.setAchievedDate(LocalDate.now());

        memberStampRepository.save(memberStamp);

        Long count = memberStampRepository.countByMemberIdAndStampId(targetMemberId, stampId);

        if (count >= 5) {
            boolean hasBadge = memberBadgeRepository.existsByMemberIdAndBadgeId(targetMemberId, stampId); // badgeId == stampId 라고 가정

            if (!hasBadge) {
                MemberBadge badge = new MemberBadge();
                badge.setMemberId(targetMemberId);
                badge.setBadgeId(stampId); // 예: 뱃지 ID를 stampId로 매핑
                badge.setAchievedDate(LocalDate.now());
                memberBadgeRepository.save(badge);
            }
        }
    }
}
