package com.leesang.mylocaldiary.stamp.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "member_badge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "achieved_date", columnDefinition = "DATE")
    private LocalDate achievedDate;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "badge_id")
    private Long badgeId;
}
