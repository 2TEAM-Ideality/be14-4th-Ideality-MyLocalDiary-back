DROP TABLE IF EXISTS member_stamp;
DROP TABLE IF EXISTS follow;
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS suspension;
DROP TABLE IF EXISTS report;
DROP TABLE IF EXISTS report_reason;
DROP TABLE IF EXISTS stamp;
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
  email VARCHAR(255),
  birth VARCHAR(255),
  bio TEXT,
  created_at VARCHAR(255),
  deleted_at VARCHAR(255),
  profile_image TEXT,
  status VARCHAR(255),
  suspension_count INT,
  profile_music VARCHAR(255),
  provider VARCHAR(255),
  provider_id VARCHAR(255),
  is_public BOOLEAN,
  is_deleted BOOLEAN,
  role VARCHAR(255)
);

CREATE TABLE post (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255),
  post TEXT,
  diary TEXT,
  created_at VARCHAR(255),
  updated_at VARCHAR(255),
  likes_count INT,
  member_id INT,
  is_deleted BOOLEAN,
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE likes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  created_at VARCHAR(255),
  post_id INT,
  member_id INT,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE comment(
  id INT PRIMARY KEY AUTO_INCREMENT,
  content TEXT NOT NULL,
  created_at VARCHAR(255),
  updated_at VARCHAR(255),
  post_id INT,
  member_id INT,
  parent_comment_id INT,
  is_deleted BOOLEAN,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id),
  CONSTRaint FOREIGN KEY (parent_comment_id) REFERENCES comment(id)
);

CREATE TABLE place (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  latitude DECIMAL(10, 7),
  longitude DECIMAL(10, 7),
  orders INT,
  thumbnail_image TEXT,
  post_id INT,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE photo (
  id INT PRIMARY KEY AUTO_INCREMENT,
  image_url VARCHAR(255),
  orders INT,
  post_id INT,
  FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE stamp (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  description TEXT
);

CREATE TABLE member_stamp (
  id INT AUTO_INCREMENT PRIMARY KEY,
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
  FOREIGN KEY (following_member_id) REFERENCES member(id),
  FOREIGN KEY (follow_target_member_id) REFERENCES member(id)
);

CREATE TABLE notification (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(255),
  target_id INT,
  content TEXT,
  is_read BOOLEAN,
  created_at VARCHAR(255),
  recieving_member_id INT NOT NULL,
  FOREIGN KEY (recieving_member_id) REFERENCES member(id)
);

CREATE TABLE suspension (
  id INT AUTO_INCREMENT PRIMARY KEY,
  suspension_start_date VARCHAR(255),
  suspension_end_date VARCHAR(255),
  type VARCHAR(255),
  member_id INT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE report_reason (
   id INT AUTO_INCREMENT PRIMARY KEY,
   reason TEXT
);

CREATE TABLE report (
    id INT AUTO_INCREMENT PRIMARY KEY,
    created_at VARCHAR(255),
    report_type VARCHAR(255),
    reported_id INT,
    content TEXT,
    status VARCHAR(255),
    member_id INT NOT NULL,
    report_reason_id INT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (report_reason_id) REFERENCES report_reason(id)
);

