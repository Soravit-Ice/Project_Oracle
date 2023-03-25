package com.project.projectmfec1.service;

import com.project.projectmfec1.entity.TimeSheetEntity;
import com.project.projectmfec1.entity.TokenEntity;
import com.project.projectmfec1.entity.UserEntity;
import com.project.projectmfec1.model.TimeSheetModel;
import com.project.projectmfec1.repository.TimeSheetRepository;
import com.project.projectmfec1.repository.TokenRepository;
import com.project.projectmfec1.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
public class UtilsService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private UserService userService;
    public String auth(){
        List<TokenEntity> tokenEntityList = tokenRepository.findAll();
        if (!CollectionUtils.isEmpty(tokenEntityList)) {
            return "loginMode";
        }else{
            return "logoutMode";
        }
    }


    public String saveTimeSheet(TimeSheetModel timeSheetModel){
        if(Objects.nonNull(timeSheetModel)){
            TimeSheetEntity timeSheetEntity = new TimeSheetEntity();
            UserEntity userEntity = userService.findUserLogin();
            BeanUtils.copyProperties(timeSheetModel,timeSheetEntity);
            timeSheetEntity.setUserEntity(userEntity);
            timeSheetRepository.save(timeSheetEntity);
        }
      return "saveSuccess";
    }

    public  List<TimeSheetEntity> findAllTimeSheet(){
        UserEntity userEntity = userService.findUserLogin();
        return timeSheetRepository.findAllByUserEntity(userEntity);
    }
}
