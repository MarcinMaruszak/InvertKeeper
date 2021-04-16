package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.DTO.ChangePasswordDTO;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    @Lazy
    private UserDetailsServiceImpl userServices;


    @PostMapping(path = "/register")
    public @ResponseBody
    void register(@Valid @RequestBody User user, HttpServletRequest request) {
        userServices.register(user, request);
    }

    @GetMapping(path = "/profile")
    public String profile(Model model) {
        return userServices.profileHTML(model);
    }

    @PostMapping(path = "/changePass")
    public @ResponseBody
    void changePass(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        userServices.changePass(changePasswordDTO);
    }

    @RequestMapping(path = "/confirmAccount")
    public String confirmAccount(@RequestParam("token") String token, Model model) {
        return userServices.activateUserHTML(token, model);
    }

    @PostMapping(path = "/resendToken")
    public @ResponseBody
    void resendToken(@RequestParam("email") String email, HttpServletRequest request) {
        userServices.resendToken(email, request);
    }

    @PostMapping(path = "/requestPasswordReset")
    public @ResponseBody
    void requestPasswordReset(@RequestParam("email") String email, HttpServletRequest request) {
        userServices.resetPasswordRequest(email, request);
    }

    @RequestMapping(path = "/resetPassword")
    public String resetPasswordHTML(@RequestParam("token") String token, Model model) {
        return userServices.resetPasswordHTML(token, model);
    }

    @PostMapping(path = "/resetPassword")
    public @ResponseBody
    void resetPassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        userServices.resetPassword(changePasswordDTO);
    }
}




