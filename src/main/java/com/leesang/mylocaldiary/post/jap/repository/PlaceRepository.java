package com.leesang.mylocaldiary.post.jap.repository;

import com.leesang.mylocaldiary.post.jap.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}