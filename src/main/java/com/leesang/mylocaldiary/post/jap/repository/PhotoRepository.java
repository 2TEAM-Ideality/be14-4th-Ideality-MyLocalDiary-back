package com.leesang.mylocaldiary.post.jap.repository;

import com.leesang.mylocaldiary.post.jap.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}