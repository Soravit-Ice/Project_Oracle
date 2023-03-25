package com.project.projectmfec1.controller;

import com.project.projectmfec1.entity.UserEntity;
import com.project.projectmfec1.model.ChangePasswordModel;
import com.project.projectmfec1.model.RegisterModel;
import com.project.projectmfec1.model.SignInModel;
import com.project.projectmfec1.service.UserService;
import com.project.projectmfec1.service.UtilsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
public class UserController {
    // page ใช้อะไรอะไร  , database ใช้อะไร  Final
    @Autowired
    private UserService userService;

    @Autowired
    private UtilsService utilsService;


    @GetMapping("/register")
    public String register(Model model){
        RegisterModel registerModel = new RegisterModel();
        String auth =  utilsService.auth();
        model.addAttribute("mode",auth);
        model.addAttribute("RegisterModel" ,registerModel);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("RegisterModel") RegisterModel registerModel){
        System.out.println(registerModel);
        String result = userService.register(registerModel);
        if(result.equals("success")){
            return "redirect:/login";
        }else{
            return "register";
        }
    }

    @GetMapping("/login")
    public String login(Model model){
        SignInModel signInModel = new SignInModel();
        String auth =  utilsService.auth();
        model.addAttribute("mode",auth);
        model.addAttribute("SignInModel" , signInModel);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("SignInModel") SignInModel signInModel , HttpSession session){
       String result = userService.login(signInModel);
       if(result.equals("success")){
           return "redirect:/";
       }else{
           return "login";
       }
    }

    @GetMapping("/logout")
    public String logout(){
        userService.logOut();
        return "redirect:/";
    }


    @GetMapping("/profile")
    public String profile(Model model){
        String auth =  utilsService.auth();
        UserEntity data  = userService.findUserLogin();
        RegisterModel registerModel = new RegisterModel();
        registerModel.setId(data.getId());
        registerModel.setUserName(data.getUserName());
        registerModel.setPassword(data.getPassword());
        registerModel.setEmail(data.getEmail());
        registerModel.setStudentId(data.getStudentId());
        registerModel.setFirstName(data.getFirstName());
        registerModel.setLastName(data.getLastName());
        if(null != data.getImageProfile()) {
            registerModel.setImage(Base64.getEncoder().encodeToString(data.getImageProfile()));
        }

        ChangePasswordModel changePasswordModel = new ChangePasswordModel();
        changePasswordModel.setId(data.getId());
        model.addAttribute("mode",auth);
        model.addAttribute("RegisterModel" , registerModel);
        model.addAttribute("ChangePasswordModel" , changePasswordModel);
        return "profile";
    }



    @PostMapping("/profile")
    public String profileSave(@RequestParam("files") MultipartFile[] multipartFiles, @ModelAttribute("RegisterModel") RegisterModel registerModel) throws IOException {
        String data = userService.profileSave(multipartFiles , registerModel);
        return "redirect:/";
    }


    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("ChangePasswordModel") ChangePasswordModel changePasswordModel) throws IOException {
        String data = userService.changePassword(changePasswordModel);
        return "redirect:/";
    }


}
