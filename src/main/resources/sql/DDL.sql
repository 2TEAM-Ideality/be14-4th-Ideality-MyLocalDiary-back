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
<<<<<<< HEAD
  email VARCHAR(255) NOT NULL UNIQUE,
  birth VARCHAR(255) NOT NULL,
=======
  name VARCHAR(255),
  email VARCHAR(255) NOT NULL UNIQUE,
  birth VARCHAR(255),
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
  nickname VARCHAR(255) NOT NULL UNIQUE,
  bio TEXT,
  created_at VARCHAR(255),
  deleted_at VARCHAR(255),
  profile_image TEXT,
<<<<<<< HEAD
  status VARCHAR(255),
=======
  status VARCHAR(255) NOT NULL,
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
  suspension_count INT NOT NULL DEFAULT 0,
  profile_music VARCHAR(255),
  provider VARCHAR(255),
  provider_id VARCHAR(255),
  is_public BOOLEAN NOT NULL DEFAULT FALSE,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
<<<<<<< HEAD
  role VARCHAR(255)
=======
  role VARCHAR(255) NOT NULL,
  CHECK ( status IN ('SUSPENDED', 'DELETED', 'ACTIVE')),
  CHECK ( role IN ('MEMBER', 'ADMIN') )
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
);

CREATE TABLE post (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  post TEXT,
<<<<<<< HEAD
  diary TEXT,
#   날짜는 VARCHAR에서 수정하지 않았으므로, default 설정을 하지 않았음
=======
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
  created_at VARCHAR(255),
  updated_at VARCHAR(255),
  likes_count INT NOT NULL DEFAULT 0,
  member_id INT NOT NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE likes (
  id INT PRIMARY KEY AUTO_INCREMENT,
<<<<<<< HEAD
#   날짜는 VARCHAR에서 수정하지 않았으므로, default 설정을 하지 않았음
  created_at VARCHAR(255),
  post_id INT NOT NULL,
  member_id INT NOT NUll,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id)
=======
  created_at VARCHAR(255),
  member_id INT NOT NUll,
  type VARCHAR(255) NOT NULL,
  target_id INT NOT NULL,
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id),
  CHECK (type IN ('POST', 'COMMENT')),
  INDEX idx_user_target (member_id, type, target_id)
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
);

CREATE TABLE comment(
  id INT PRIMARY KEY AUTO_INCREMENT,
  content TEXT NOT NULL,
<<<<<<< HEAD
#   날짜는 VARCHAR에서 수정하지 않았으므로, default 설정을 하지 않았음
=======
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
  created_at VARCHAR(255),
  updated_at VARCHAR(255),
  post_id INT NOT NULL,
  member_id INT NOT NULL,
  parent_comment_id INT,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
<<<<<<< HEAD
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id),
  CONSTRaint FOREIGN KEY (parent_comment_id) REFERENCES comment(id)
=======
  target_member_id INT,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id),
  CONSTRAINT FOREIGN KEY (parent_comment_id) REFERENCES comment(id),
  CONSTRAINT FOREIGN KEY (target_member_id) REFERENCES member(id),
-- 해당 포스트에 달린 댓글을 조회할 때 사용하기 위해
  INDEX idx_comment_post_id (post_id),
-- 해당 부모 댓글에 달린 댓글들을 조회할 때 사용하기 위해
  INDEX idx_comment_parent_comment_id (parent_comment_id)
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
);

CREATE TABLE place (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  latitude DECIMAL(10, 7) NOT NULL,
  longitude DECIMAL(10, 7) NOT NULL,
  orders INT NOT NULL,
  thumbnail_image TEXT,
  post_id INT NOT NULL,
<<<<<<< HEAD
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id)
=======
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
#   place 테이블에도 index 추가 해당 포스트에 달린 장소를 조회할 때 사용
  INDEX idx_place_post_id (post_id)
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
);

CREATE TABLE photo (
  id INT PRIMARY KEY AUTO_INCREMENT,
  image_url VARCHAR(255) NOT NULL,
  orders INT NOT NULL,
  post_id INT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE stamp (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL
);

CREATE TABLE member_stamp (
  id INT AUTO_INCREMENT PRIMARY KEY,
#   날짜는 VARCHAR에서 수정하지 않았으므로, default 설정을 하지 않았음
  achieved_date VARCHAR(255),
  member_id INT NOT NULL,
  stamp_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id),
  FOREIGN KEY (stamp_id) REFERENCES stamp(id)
);

CREATE TABLE badge (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL
);

CREATE TABLE member_badge (
  id INT AUTO_INCREMENT PRIMARY KEY,
#   날짜는 VARCHAR에서 수정하지 않았으므로, default 설정을 하지 않았음
  achieved_date VARCHAR(255),
  member_id INT NOT NULL,
  stamp_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id),
  FOREIGN KEY (stamp_id) REFERENCES stamp(id)
);

CREATE TABLE follow (
  id INT AUTO_INCREMENT PRIMARY KEY,
  following_member_id INT NOT NULL,
  follow_target_member_id INT NOT NULL,
<<<<<<< HEAD
  FOREIGN KEY (following_member_id) REFERENCES member(id),
  FOREIGN KEY (follow_target_member_id) REFERENCES member(id),
  UNIQUE (following_member_id, follow_target_member_id)
=======
  status BOOLEAN,
  FOREIGN KEY (following_member_id) REFERENCES member(id),
  FOREIGN KEY (follow_target_member_id) REFERENCES member(id),
  UNIQUE (following_member_id, follow_target_member_id),
  INDEX idx_follow_from (following_member_id),                    -- 내가 팔로우한 사람 조회용
  INDEX idx_follow_to (follow_target_member_id),
  INDEX idx_follow (following_member_id, follow_target_member_id)
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
);

CREATE TABLE notification (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(255) NOT NULL,
  target_id INT NOT NULL,
  content TEXT NOT NULL,
  is_read BOOLEAN NOT NULL DEFAULT FALSE,
#   날짜는 VARCHAR에서 수정하지 않았으므로, default 설정을 하지 않았음
  created_at VARCHAR(255),
  recieving_member_id INT NOT NULL,
  FOREIGN KEY (recieving_member_id) REFERENCES member(id)
);

CREATE TABLE suspension (
  id INT AUTO_INCREMENT PRIMARY KEY,
#   날짜는 VARCHAR에서 수정하지 않았으므로, default 설정을 하지 않았음
  suspension_start_date VARCHAR(255),
  suspension_end_date VARCHAR(255),
<<<<<<< HEAD
=======
  # 아직 확실한 enum을 정하지 않아, 제외
>>>>>>> 20b969b6efc710f2c8a8023209971e0908a2308a
  type VARCHAR(255) NOT NULL,
  member_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE report_reason (
   id INT AUTO_INCREMENT PRIMARY KEY,
   reason TEXT NOT NULL
);

CREATE TABLE report (
    id INT AUTO_INCREMENT PRIMARY KEY,
#   날짜는 VARCHAR에서 수정하지 않았으므로, default 설정을 하지 않았음
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

