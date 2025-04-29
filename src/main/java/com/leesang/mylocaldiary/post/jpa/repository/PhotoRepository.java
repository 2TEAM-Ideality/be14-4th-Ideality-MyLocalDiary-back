package com.leesang.mylocaldiary.post.jpa.repository;

import com.leesang.mylocaldiary.post.jpa.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}