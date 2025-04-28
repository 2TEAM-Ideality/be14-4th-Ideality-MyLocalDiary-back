INSERT INTO member (id, login_id, password, email, birth, nickname, is_public, is_deleted)
VALUES
    (1, 'user1', 'password1', 'user1@example.com', '1990-01-01', 'UserOne', TRUE, FALSE),
    (2, 'user2', 'password2', 'user2@example.com', '1992-02-02', 'UserTwo', TRUE, FALSE),
    (3, 'user3', 'password3', 'user3@example.com', '1993-03-03', 'UserThree', TRUE, FALSE);

INSERT INTO follow (id, following_member_id, follow_target_member_id)
VALUES
    (1, 1, 2), -- 1번 유저가 2번 유저를 팔로우
    (2, 1, 3); -- 1번 유저가 3번 유저를 팔로우

INSERT INTO post (id, title, post, diary, created_at, updated_at, likes_count, member_id, is_deleted)
VALUES
    (1, '내 게시글 1', '내 첫번째 게시글 본문', '내 첫번째 다이어리', '2025-04-28', '2025-04-28', 0, 1, FALSE),
    (2, '내 게시글 2', '내 두번째 게시글 본문', '내 두번째 다이어리', '2025-04-28', '2025-04-28', 0, 1, FALSE),
    (3, '팔로우한 사람 게시글 1', '2번 유저 게시글 본문', '2번 유저 다이어리', '2025-04-28', '2025-04-28', 0, 2, FALSE),
    (4, '팔로우한 사람 게시글 2', '3번 유저 게시글 본문', '3번 유저 다이어리', '2025-04-28', '2025-04-28', 0, 3, FALSE);

INSERT INTO photo (id, image_url, orders, post_id)
VALUES
    (1, 'https://dummyimage.com/600x400/000/fff&text=Post1-Photo1', 1, 1),
    (2, 'https://dummyimage.com/600x400/000/fff&text=Post2-Photo1', 1, 2),
    (3, 'https://dummyimage.com/600x400/000/fff&text=Post3-Photo1', 1, 3),
    (4, 'https://dummyimage.com/600x400/000/fff&text=Post4-Photo1', 1, 4);

INSERT INTO place (id, name, latitude, longitude, orders, thumbnail_image, post_id)
VALUES
    (1, '장소 A', 37.5665, 126.9780, 1, NULL, 1),
    (2, '장소 B', 37.5678, 126.9820, 1, NULL, 2),
    (3, '장소 C', 37.5700, 126.9900, 1, NULL, 3),
    (4, '장소 D', 37.5720, 126.9950, 1, NULL, 4);