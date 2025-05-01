package com.leesang.mylocaldiary.notification.repository;

import com.leesang.mylocaldiary.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecievingMemberIdOrderByIdDesc(Long memberId);

}
