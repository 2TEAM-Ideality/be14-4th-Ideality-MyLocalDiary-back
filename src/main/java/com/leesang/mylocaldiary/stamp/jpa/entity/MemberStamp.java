package com.leesang.mylocaldiary.stamp.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "member_stamp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "achieved_date", columnDefinition = "DATE")
    private LocalDate achievedDate;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "stamp_id")
    private Long stampId;
}
