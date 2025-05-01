DROP TABLE IF EXISTS member_stamp;
DROP TABLE IF EXISTS member_badge;
DROP TABLE IF EXISTS follow;
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS suspension;
DROP TABLE IF EXISTS report;
DROP TABLE IF EXISTS report_reason;
DROP TABLE IF EXISTS stamp;
DROP TABLE IF EXISTS badge;
DROP TABLE IF EXISTS photo;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
  id INT PRIMARY KEY AUTO_INCREMENT,
  login_id VARCHAR(255),
  password VARCHAR(255),
  name VARCHAR(255),
  email VARCHAR(255) NOT NULL UNIQUE,
  birth VARCHAR(255),
  nickname VARCHAR(255) NOT NULL UNIQUE,
  bio TEXT,
  created_at VARCHAR(255),
  deleted_at VARCHAR(255),
  profile_image TEXT,
  status VARCHAR(255) NOT NULL,
  suspension_count INT NOT NULL DEFAULT 0,
  report_count INT NOT NULL DEFAULT 0,
  profile_music VARCHAR(255),
  provider VARCHAR(255) NOT NULL,
  provider_id VARCHAR(255),
  is_public BOOLEAN NOT NULL DEFAULT FALSE,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  role VARCHAR(255) NOT NULL,
  CHECK (status IN ('SUSPENDED', 'DELETED', 'ACTIVE')),
  CHECK (provider IN ('LOCAL', 'KAKAO')),
  CHECK (role IN ('MEMBER', 'ADMIN'))
);

CREATE TABLE post (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  post TEXT,
  diary TEXT,
  created_at VARCHAR(255),
  updated_at VARCHAR(255),
  likes_count INT NOT NULL DEFAULT 0,
  member_id INT NOT NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE likes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  created_at VARCHAR(255),
  member_id INT NOT NULL,
  type VARCHAR(255) NOT NULL,
  target_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id),
  CHECK (type IN ('POST', 'COMMENT')),
  INDEX idx_user_target (member_id, type, target_id)
);

CREATE TABLE comment(
  id INT PRIMARY KEY AUTO_INCREMENT,
  content TEXT NOT NULL,
  created_at VARCHAR(255),
  updated_at VARCHAR(255),
  likes_count INT NOT NULL DEFAULT 0,
  post_id INT NOT NULL,
  member_id INT NOT NULL,
  parent_comment_id INT,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  target_member_id INT,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id),
  CONSTRAINT FOREIGN KEY (parent_comment_id) REFERENCES comment(id),
  CONSTRAINT FOREIGN KEY (target_member_id) REFERENCES member(id),
-- 해당 포스트에 달린 댓글을 조회할 때 사용하기 위해
  INDEX idx_comment_post_id (post_id),
-- 해당 부모 댓글에 달린 댓글들을 조회할 때 사용하기 위해
  INDEX idx_comment_parent_comment_id (parent_comment_id)
);

CREATE TABLE place (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  latitude DECIMAL(10, 7) NOT NULL,
  longitude DECIMAL(10, 7) NOT NULL,
  orders INT NOT NULL,
  thumbnail_image TEXT,
  post_id INT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES post(id),
  INDEX idx_place_post_id (post_id)
);

CREATE TABLE photo (
  id INT PRIMARY KEY AUTO_INCREMENT,
  image_url VARCHAR(255) NOT NULL,
  orders INT NOT NULL,
  post_id INT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE stamp (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL
);

CREATE TABLE member_stamp (
  id INT PRIMARY KEY AUTO_INCREMENT,
  achieved_date VARCHAR(255),
  member_id INT NOT NULL,
  stamp_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id),
  FOREIGN KEY (stamp_id) REFERENCES stamp(id)
);

CREATE TABLE badge (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL
);

CREATE TABLE member_badge (
  id INT PRIMARY KEY AUTO_INCREMENT,
  achieved_date VARCHAR(255),
  member_id INT NOT NULL,
  badge_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id),
  FOREIGN KEY (badge_id) REFERENCES stamp(id)
);

CREATE TABLE follow (
  id INT PRIMARY KEY AUTO_INCREMENT,
  following_member_id INT NOT NULL,
  follow_target_member_id INT NOT NULL,
  status BOOLEAN,
  FOREIGN KEY (following_member_id) REFERENCES member(id),
  FOREIGN KEY (follow_target_member_id) REFERENCES member(id),
  UNIQUE (following_member_id, follow_target_member_id),
  INDEX idx_follow_from (following_member_id),
  INDEX idx_follow_to (follow_target_member_id),
  INDEX idx_follow (following_member_id, follow_target_member_id)
);

CREATE TABLE notification (
  id INT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(255) NOT NULL,
  target_id INT NOT NULL,
  content TEXT NOT NULL,
  is_read BOOLEAN NOT NULL DEFAULT FALSE,
  created_at VARCHAR(255),
  recieving_member_id INT NOT NULL,
  FOREIGN KEY (recieving_member_id) REFERENCES member(id)
);

CREATE TABLE suspension (
  id INT PRIMARY KEY AUTO_INCREMENT,
  suspension_start_date VARCHAR(255),
  suspension_end_date VARCHAR(255),
  type VARCHAR(255) NOT NULL,
  member_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE report_reason (
  id INT PRIMARY KEY AUTO_INCREMENT,
  reason TEXT NOT NULL
);

CREATE TABLE report (
  id INT PRIMARY KEY AUTO_INCREMENT,
  created_at VARCHAR(255),
  report_type VARCHAR(255) NOT NULL,
  reported_id INT NOT NULL,
  content TEXT,
  status VARCHAR(255),
  member_id INT NOT NULL,
  report_reason_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id),
  FOREIGN KEY (report_reason_id) REFERENCES report_reason(id)
);

