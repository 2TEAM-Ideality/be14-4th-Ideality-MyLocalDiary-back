-- member (회원)
INSERT INTO member (id, login_id, password, name, email, nickname, bio, created_at, status, role, profile_image)
VALUES
    (1, 'user1', 'pw1', '민수', 'minsu@example.com', '민수짱', '여행을 좋아하는 사람입니다.', '2025-04-01', 'ACTIVE', 'MEMBER', 'https://randomuser.me/api/portraits/men/1.jpg'),
    (2, 'user2', 'pw2', '지연', 'jiyeon@example.com', '지연쨩', '카페 탐방러.', '2025-04-02', 'ACTIVE', 'MEMBER', 'https://randomuser.me/api/portraits/women/2.jpg'),
    (3, 'user3', 'pw3', '동혁', 'donghyuk@example.com', '혁혁혁', '서핑을 좋아하는 개발자.', '2025-04-03', 'ACTIVE', 'MEMBER', 'https://randomuser.me/api/portraits/men/3.jpg');

-- post (게시글)
INSERT INTO post (id, title, post, created_at, updated_at, likes_count, member_id, is_deleted)
VALUES
    (1, '봄날 벚꽃 나들이', '진짜 예뻤다', '2025-04-10', '2025-04-10', 2, 1, FALSE),
    (2, '카페 투어', '커피 너무 맛있어', '2025-04-12', '2025-04-12', 1, 2, FALSE),
    (3, '강릉 여행', '바다가 최고야', '2025-04-15', '2025-04-15', 3, 3, FALSE);

-- place (장소)
INSERT INTO place (id, name, latitude, longitude, orders, thumbnail_image, post_id)
VALUES
    (1, '여의도공원', 37.5265, 126.9259, 1, 'https://placeimg.com/640/480/nature', 1),
    (2, '브런치카페', 37.5412, 126.9860, 1, 'https://placeimg.com/640/480/coffee', 2),
    (3, '경포대 해수욕장', 37.8047, 128.9027, 1, 'https://placeimg.com/640/480/beach', 3);

-- photo (사진)
INSERT INTO photo (id, image_url, orders, post_id)
VALUES
    (1, 'https://placeimg.com/640/480/nature', 1, 1),
    (2, 'https://placeimg.com/640/480/coffee', 1, 2),
    (3, 'https://placeimg.com/640/480/beach', 1, 3);

-- likes (좋아요)
INSERT INTO likes (id, created_at, member_id, type, target_id)
VALUES
    (1, '2025-04-11', 2, 'POST', 1),
    (2, '2025-04-11', 3, 'POST', 1),
    (3, '2025-04-13', 1, 'POST', 2),
    (4, '2025-04-15', 1, 'COMMENT', 1),
    (5, '2025-04-16', 2, 'COMMENT', 1);

-- comment (댓글)
INSERT INTO comment (id, content, created_at, likes_count,post_id, member_id, parent_comment_id, is_deleted)
VALUES
    (1, '정말 이뻤겠네요!', '2025-04-11', 2,1, 2, NULL, FALSE),
    (2, '다음에 같이 가요', '2025-04-11', 0, 1, 3, 1, FALSE);

-- follow (팔로우)
INSERT INTO follow (id, following_member_id, follow_target_member_id, status)
VALUES
    (1, 1, 2, TRUE),
    (2, 1, 3, TRUE);

-- stamp
INSERT INTO stamp (id, name, description)
VALUES
    (1, '카페냥', '카페다냥'),
    (2, '산책냥', '산책이다냥'),
    (3, '꽐라냥', '취했다냥'),
    (4, '독서냥', '독서다냥'),
    (5, '맛집냥', '맛집이다냥'),
    (6, '영화냥', '영화다냥');

-- member_stamp
INSERT INTO member_stamp (id, achieved_date, member_id, stamp_id)
VALUES
    (1, '2025-04-11', 1, 1),
    (2, '2025-04-12', 1, 1),
    (3, '2025-04-13', 1, 2),
    (4, '2025-04-14', 1, 4),
    (5, '2025-04-15', 1, 6);

-- badge
INSERT INTO badge (id, name, description)
VALUES
    (1, '카페냥', '카페 5회 이상'),
    (2, '산책냥', '산책 5회 이상'),
    (3, '꽐라냥', '술집 5회 이상'),
    (4, '독서냥', '서점 5회 이상'),
    (5, '맛집냥', '맛집 5회 이상'),
    (6, '영화냥', '영화관 5회 이상');