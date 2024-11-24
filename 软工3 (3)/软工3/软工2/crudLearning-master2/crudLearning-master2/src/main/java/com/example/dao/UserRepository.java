package com.example.dao;

import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user where name like %?1%", nativeQuery = true)
    Page<User> findByNameLike(String name, Pageable pageRequest);

    // 批量更新收藏状态
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isFavorite = ?2 WHERE u.id IN ?1")
    void updateIsFavoriteByIdIn(List<Long> ids, Boolean isFavorite);

    // 按照isFavorite字段查询所有用户
    List<User> findByIsFavorite(Boolean isFavorite);
}
