-- member (회원)
INSERT INTO member (id, login_id, password, name, email, nickname, bio, created_at, status, role, profile_image)
VALUES
    (1, 'user1', '$2a$12$Ws4/SmG865uPZwGYGJlPu.uKZMQomWa8GYTrFAqJ4EsnFTm7XBD2O', '민수', 'minsu@example.com', '민수짱', '여행을 좋아하는 사람입니다.', '2025-04-01', 'ACTIVE', 'MEMBER', 'https://randomuser.me/api/portraits/men/1.jpg'),
    (2, 'user2', '$2a$12$lbY6ZRT88d6O8OnRfy1Phebqmm1tpxXzRHZX1OWSzNoeV4Xn6mD62', '지연', 'jiyeon@example.com', '지연쨩', '카페 탐방러.', '2025-04-02', 'ACTIVE', 'MEMBER', 'https://randomuser.me/api/portraits/women/2.jpg'),
    (3, 'user3', '$2a$12$SJYlhyzE4ry200.3ela9dOzwK4uaL.bbzEXhevtzSEsq2MbDC9JP.', '동혁', 'donghyuk@example.com', '혁혁혁', '서핑을 좋아하는 개발자.', '2025-04-03', 'ACTIVE', 'MEMBER', 'https://randomuser.me/api/portraits/men/3.jpg');

insert into member
(login_id, password, name, email, nickname, created_at, status, role, profile_image)
values
    (
        'admin01',
        '$2a$12$reDJjzovF8o.IYnFdcbtNuEoS0GKrS6.23yWYUB3stQ5wB2YftHcG',
        '관리자',
        'admin01@test.com',
        'ADMIN',
        '2000-03-09',
        'ACTIVE',
        'ADMIN',
        null
    );
