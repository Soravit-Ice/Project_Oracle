package com.project.projectmfec1.repository;


import com.project.projectmfec1.entity.UserEntity;
import com.project.projectmfec1.model.RegisterModel;
import jakarta.persistence.Id;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface  UserRepository extends JpaRepository<UserEntity, Long> {
        UserEntity findByUserName(String userName);
}
