package com.leesang.mylocaldiary.post.jpa.repository;

import com.leesang.mylocaldiary.post.jpa.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}