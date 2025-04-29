-- 회원 정보
INSERT INTO member (id, login_id, password, name, email, birth, nickname, bio, created_at, profile_image, status, role)
VALUES
    (1, 'user1', 'pass1', '사용자1', 'user1@example.com', '1990-01-01', 'nick1', '자기소개1', '2025-04-29 03:34:31', 'https://randomuser.me/api/portraits/men/1.jpg', 'ACTIVE', 'MEMBER'),
    (2, 'user2', 'pass2', '사용자2', 'user2@example.com', '1990-01-01', 'nick2', '자기소개2', '2025-04-29 03:34:31', 'https://randomuser.me/api/portraits/men/2.jpg', 'ACTIVE', 'MEMBER'),
    (3, 'user3', 'pass3', '사용자3', 'user3@example.com', '1990-01-01', 'nick3', '자기소개3', '2025-04-29 03:34:31', 'https://randomuser.me/api/portraits/men/3.jpg', 'ACTIVE', 'MEMBER');

-- 게시글
INSERT INTO post (id, title, post, created_at, updated_at, member_id)
VALUES
    (1, '제목1', '본문 내용 1', '2025-04-29 03:34:31', '2025-04-29 03:34:31', 1),
    (2, '제목2', '본문 내용 2', '2025-04-29 03:34:31', '2025-04-29 03:34:31', 2),
    (3, '제목3', '본문 내용 3', '2025-04-29 03:34:31', '2025-04-29 03:34:31', 3);

-- 장소
INSERT INTO place (name, latitude, longitude, orders, thumbnail_image, post_id)
VALUES
    ('장소1', 37.51, 126.91, 0, 'https://img.freepik.com/premium-vector/cute-cat-vector-illustration_961307-8342.jpg', 1),
    ('장소2', 37.52, 126.92, 0, 'https://img.freepik.com/premium-vector/cute-cat-vector-illustration_961307-8342.jpg', 2),
    ('장소3', 37.53, 126.93, 0, 'https://img.freepik.com/premium-vector/cute-cat-vector-illustration_961307-8342.jpg', 3);

-- 사진
INSERT INTO photo (image_url, orders, post_id)
VALUES
    ('https://picsum.photos/200/300?random=1', 0, 1),
    ('https://picsum.photos/200/300?random=2', 0, 2),
    ('https://picsum.photos/200/300?random=3', 0, 3);

-- 댓글
INSERT INTO comment (content, created_at, updated_at, post_id, member_id)
VALUES
    ('댓글 내용 1', '2025-04-29 03:34:31', '2025-04-29 03:34:31', 1, 2),
    ('댓글 내용 2', '2025-04-29 03:34:31', '2025-04-29 03:34:31', 2, 1),
    ('댓글 내용 3', '2025-04-29 03:34:31', '2025-04-29 03:34:31', 3, 1);

-- 좋아요
INSERT INTO likes (created_at, member_id, type, target_id)
VALUES
    (NOW(), 2, 'POST', 1),
    (NOW(), 2, 'POST', 2),
    (NOW(), 3, 'COMMENT', 1);