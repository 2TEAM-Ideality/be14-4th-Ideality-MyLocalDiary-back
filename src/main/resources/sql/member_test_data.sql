-- member (회원)
-- 비밀번호는 pw1,pw2,pw3,pw4
INSERT INTO member
(id, login_id, password, name, email, nickname, bio, created_at, status, role, profile_music, birth, provider,is_public)
VALUES
    (1,
     'user1',
     '$2a$12$Ws4/SmG865uPZwGYGJlPu.uKZMQomWa8GYTrFAqJ4EsnFTm7XBD2O',
     '민수', 'minsu@example.com',
     '민수짱',
     '여행을 좋아하는 사람입니다.',
     '2025-04-01',
     'ACTIVE',
     'MEMBER',
     'https://rococo-cocada-2c23e0.netlify.app/audio/aespa - Supernova.mp3',
     '1997-10-10',
     'LOCAL',
     1),
    (2,
     'user2',
     '$2a$12$lbY6ZRT88d6O8OnRfy1Phebqmm1tpxXzRHZX1OWSzNoeV4Xn6mD62',
     '혜영',
     'hyeyoung@example.com',
     '혜영쓰',
     '카페 탐방러.',
     '2025-04-02',
     'ACTIVE',
     'MEMBER',
     'https://rococo-cocada-2c23e0.netlify.app/audio/tws (투어스)-첫 만남은 계획대로 되지 않아.mp3',
     '2002-10-10',
     'LOCAL',
     0),
    (3,
     'user3',
     '$2a$12$SJYlhyzE4ry200.3ela9dOzwK4uaL.bbzEXhevtzSEsq2MbDC9JP.',
     '동한',
     'dongdong@example.com',
     '동한개노답',
     '누워있는게 좋은 개발자.',
     '2025-04-03',
     'ACTIVE',
     'MEMBER',
     'https://rococo-cocada-2c23e0.netlify.app/audio/잔나비 (JANNABI) - 주저하는 연인들을 위해.mp3',
     '2000-10-10',
     'LOCAL',
     1),
    (
        4,
        'user4',
        '$2a$12$lm0g.48cm3yf4hqh.xpmbOns36lfFqO33oCHY6P6gMoPLsEf2BKPe',
        '민선',
        'minseon@example.com',
        '민선짱',
        null,
        '2025-04-03',
        'ACTIVE',
        'MEMBER',
        'https://rococo-cocada-2c23e0.netlify.app/audio/Freestyle - Y.mp3',
        '2002-10-10',
        'LOCAL',
     0
    );

insert into member
(login_id, password, name, email, nickname, created_at, status, role, profile_image, provider)
values
    (
        "admin01",
        "$2a$12$reDJjzovF8o.IYnFdcbtNuEoS0GKrS6.23yWYUB3stQ5wB2YftHcG",
        "관리자",
        "admin01@test.com",
        "ADMIN",
        "2000-03-09",
        "ACTIVE",
        "ADMIN",
        null,
        'LOCAL'
    );


INSERT INTO follow (following_member_id, follow_target_member_id, status) VALUES
                                                                              (1, 2, true),   -- user1 → 혜영쓰 (팔로잉)
                                                                              (1, 3, false),  -- user1 → 동한개노답 (대기중)
                                                                              (2, 4, true),   -- user2 → 민선짱 (팔로잉)
                                                                              (3, 1, true),   -- user3 → 민수짱 (팔로잉)
                                                                              (4, 2, false);  -- user4 → 혜영쓰 (대기중)
