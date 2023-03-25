package com.project.projectmfec1.service;

import com.project.projectmfec1.entity.TokenEntity;
import com.project.projectmfec1.entity.UserEntity;
import com.project.projectmfec1.model.ChangePasswordModel;
import com.project.projectmfec1.model.RegisterModel;
import com.project.projectmfec1.model.SignInModel;
import com.project.projectmfec1.repository.TokenRepository;
import com.project.projectmfec1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String register(RegisterModel registerModel){
        String result = "";
        UserEntity userEntity = new UserEntity();
        if(Objects.nonNull(registerModel)){
            UserEntity checkUser = userRepository.findByUserName(registerModel.getUserName());
            if(Objects.isNull(checkUser)){
                userEntity.setUserName(registerModel.getUserName());
                userEntity.setEmail(registerModel.getEmail());
                userEntity.setPassword(registerModel.getPassword());
                userEntity.setFirstName(registerModel.getFirstName());
                userEntity.setLastName(registerModel.getLastName());
                userEntity.setStudentId(registerModel.getStudentId());
                userRepository.save(userEntity);
                return "success";
            }else{
                return "alreadyUsername";
            }

        }else{
            return "fail";
        }
    }

    public String login(SignInModel signInModel){
        if(Objects.nonNull(signInModel)){
            UserEntity userEntity = userRepository.findByUserName(signInModel.getUserName());
            if(Objects.nonNull(userEntity)){
                if(signInModel.getUserName().equals(userEntity.getUserName()) && signInModel.getPassword().equals(userEntity.getPassword())){
                    TokenEntity tokenEntity = new TokenEntity();
                    tokenEntity.setToken("11e1635we3rte45");
                    tokenRepository.save(tokenEntity);
                    return "success";
                }else{
                    return "fail";
                }
            }else{
                return "failNotFound";
            }
        }else{
            return "failDataFail";
        }
    }


    public void logOut(){
        List<TokenEntity> tokenEntityList = tokenRepository.findAll();
        tokenRepository.deleteAll(tokenEntityList);
    }

    public UserEntity findUserLogin(){
        return userRepository.findAll().get(0);
    }


    public String profileSave(MultipartFile[] multipartFiles , RegisterModel registerModel) throws IOException {
        if(Objects.nonNull(registerModel)){
            Optional<UserEntity> userEntity = userRepository.findById(registerModel.getId());
            if(userEntity.isPresent()){
                // Save the uploaded files to the database
                for (MultipartFile file : multipartFiles) {
                    userEntity.get().setImageProfile(file.getBytes());
                }
                userEntity.get().setUserName(registerModel.getUserName());
                userEntity.get().setEmail(registerModel.getEmail());
                userEntity.get().setPassword(registerModel.getPassword());
                userEntity.get().setStudentId(registerModel.getStudentId());
                userEntity.get().setFirstName(registerModel.getFirstName());
                userEntity.get().setLastName(registerModel.getLastName());
                userRepository.save(userEntity.get());
            }
        }
        return "update";
    }
    public String changePassword(ChangePasswordModel changePasswordModel) throws IOException {
        if(Objects.nonNull(changePasswordModel)){
            if(changePasswordModel.getPassword().equals(changePasswordModel.getConfirmPassword())){
                Optional<UserEntity> userEntity = userRepository.findById(changePasswordModel.getId());
                if(userEntity.isPresent()){
                    userEntity.get().setPassword(changePasswordModel.getPassword());
                    userRepository.save(userEntity.get());
                }
            }else{
                return "fail";
            }

        }
        return "update";
    }

}
