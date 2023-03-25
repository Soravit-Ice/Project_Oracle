package com.project.projectmfec1.repository;

import com.project.projectmfec1.entity.TimeSheetEntity;
import com.project.projectmfec1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Entity -> Hibernate() ในการรคุยโปรแกรม
@Repository
public interface  TimeSheetRepository extends JpaRepository<TimeSheetEntity , Long> {

    List<TimeSheetEntity> findAllByUserEntity(UserEntity userEntity);
}
