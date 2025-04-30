package com.leesang.mylocaldiary.follow.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follow", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"followingMemberId", "followTargetMemberId"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followingMemberId;
    private Long followTargetMemberId;

    private Boolean status; // true: follow / false: wait
}
