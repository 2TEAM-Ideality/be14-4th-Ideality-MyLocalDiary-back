package com.leesang.mylocaldiary.admin.aggregate;

import java.time.LocalDateTime;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suspension")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuspensionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "suspension_start_date", nullable = false)
	private LocalDateTime suspensionStartDate;

	@Column(name = "suspension_end_date", nullable = false)
	private LocalDateTime suspensionEndDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 20)
	private SuspensionType type;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private MemberEntity member;

	// 정지 기간 연장
	public void setSuspensionEndDate(LocalDateTime newEndDate) {
		this.suspensionEndDate = newEndDate;
	}
}
