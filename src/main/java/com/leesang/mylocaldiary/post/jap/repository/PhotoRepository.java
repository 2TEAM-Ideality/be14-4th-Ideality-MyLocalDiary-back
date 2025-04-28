package com.leesang.mylocaldiary.post.jap.repository;

import com.leesang.mylocaldiary.post.jap.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}