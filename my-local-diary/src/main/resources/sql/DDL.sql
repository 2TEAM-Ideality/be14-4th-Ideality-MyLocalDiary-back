DROP TABLE IF EXISTS photo;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
  id INT PRIMARY KEY AUTO_INCREMENT,
  login_id VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  birth VARCHAR(255) NOT NULL,
  bio TEXT,
  created_at VARCHAR(255),
  deleted_at VARCHAR(255),
  profile_image TEXT,
  status VARCHAR(255) NOT NULL,
  suspension_count INT NOT NULL DEFAULT 0,
  profile_music VARCHAR(255),
  provider VARCHAR(255),
  provider_id VARCHAR(255),
  is_public BOOLEAN NOT NULL DEFAULT TRUE,
  role VARCHAR(255)
);

CREATE TABLE post (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255),
  post TEXT,
  diary TEXT,
  created_at VARCHAR(255),
  updated_at VARCHAR(255),
  likes_count INT NOT NULL DEFAULT 0,
  member_id INT NOT NULL,
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE likes (
  id INT PRIMARY KEY PRIMARY KEY AUTO_INCREMENT,
  created_at VARCHAR(255),
  post_id INT NOT NULL,
  member_id INT NOT NULL,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE comment(
  id INT PRIMARY KEY AUTO_INCREMENT,
  content TEXT NOT NULL,
  created_at VARCHAR(255),
  updated_at VARCHAR(255),
  post_id INT,
  member_id INT NOT NULL,
  parent_comment_id INT,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id),
  CONSTRAINT FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE place (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  latitude DECIMAL(10, 7) NOT NULL,
  longitude DECIMAL(10, 7) NOT NULL,
  orders INT,
  thumbnail_image VARCHAR(255),
  post_id INT NOT NULL,
  CONSTRAINT FOREIGN KEY (post_id) REFERENCES post(id)
);

CREATE TABLE photo (
  id INT PRIMARY KEY AUTO_INCREMENT,
  image_url VARCHAR(255) NOT NULL,
  orders INT,
  post_id INT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES post(id)
);
