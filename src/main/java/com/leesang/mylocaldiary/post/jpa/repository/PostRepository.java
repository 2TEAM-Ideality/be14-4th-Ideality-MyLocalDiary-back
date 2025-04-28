package com.leesang.mylocaldiary.post.jpa.repository;

import com.leesang.mylocaldiary.post.jpa.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}