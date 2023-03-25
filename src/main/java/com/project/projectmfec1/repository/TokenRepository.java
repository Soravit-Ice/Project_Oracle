package com.project.projectmfec1.repository;

import com.project.projectmfec1.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity,Long> {
}
