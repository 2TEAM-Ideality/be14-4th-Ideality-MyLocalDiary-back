package com.leesang.mylocaldiary.post.jap.repository;

import com.leesang.mylocaldiary.post.jap.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}