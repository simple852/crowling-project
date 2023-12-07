package com.teamproject.computerproject.repositery;

import com.teamproject.computerproject.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Transactional
    @Modifying
    @Query("update Category c set c.updateTime = ?1")
    int updateUpdateTimeBy(LocalDateTime updateTime);
    @Transactional
    @Modifying
    @Query("update Category c set c.updateTime = ?1 where c.id = ?2")
    int updateUpdateTimeById(LocalDateTime updateTime, Integer id);
}