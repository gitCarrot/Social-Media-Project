package com.gitcarrot.socialmedia.repository;

import com.gitcarrot.socialmedia.model.entity.PostEntity;
import com.gitcarrot.socialmedia.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {

    Page<PostEntity> findAllByUser(UserEntity userEntity, Pageable pageable);
}
