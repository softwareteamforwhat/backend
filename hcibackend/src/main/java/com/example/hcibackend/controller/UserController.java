package com.example.hcibackend.controller;

import com.example.hcibackend.config.UserLoginToken;
import com.example.hcibackend.po.LoginForm;
import com.example.hcibackend.po.RegisterForm;
import com.example.hcibackend.service.TokenService;
import com.example.hcibackend.service.UserService;
import com.example.hcibackend.vo.ResponseVO;
import com.example.hcibackend.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController()
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseVO login(@RequestBody LoginForm loginForm){
        UserVO userVO = userService.login(loginForm);
        if(userVO!=null){
            return ResponseVO.buildSuccess(userVO);
        }else {
            return ResponseVO.buildFailure("用户名或密码错误！");
        }
    }

    @GetMapping(value = "/code")
    public ResponseVO sendCode(@RequestParam String email){
        if(!checkEmailFormat(email)){
            return ResponseVO.buildFailure("邮箱格式不正确");
        }else if(userService.existEmail(email)){
            return ResponseVO.buildFailure("该邮箱用户已存在");
        } else {
            userService.sendCode(email);
            return ResponseVO.buildSuccess("验证码已发送");
        }
    }

    @PostMapping(value = "/register")
    public ResponseVO register(@RequestBody RegisterForm registerForm){
        int status = userService.checkCode(registerForm.getEmail(),registerForm.getCode());
        if(status==0){
            UserVO user = userService.register(registerForm);
            if(user == null){
                return ResponseVO.buildFailure("用户邮箱已存在");
            }
            return ResponseVO.buildSuccess(user);
        }else if(status==1){
            return ResponseVO.buildFailure("验证码错误");
        }else{
            return ResponseVO.buildFailure("验证码超时");
        }
    }

    @GetMapping(value = "/forget")
    public ResponseVO forget(@RequestParam String email){
        if(checkEmailFormat(email)){
            userService.sendNewPwd(email);
            return ResponseVO.buildSuccess("发送密码成功");
        }else {
            return ResponseVO.buildFailure("邮箱格式不正确");
        }
    }

    @UserLoginToken
    @PostMapping("/changeFollow")
    public ResponseVO changeFollow(@RequestParam long uid,@RequestParam String movieId){
        if(userService.changeFollow(uid,movieId)){
            return ResponseVO.buildSuccess("修改收藏状态成功");
        }else {
            return ResponseVO.buildFailure("修改收藏状态失败");
        }
    }

    @UserLoginToken
    @GetMapping("/getUserFavorite")
    public ResponseVO getUserFavorite(@RequestParam long uid){
        return ResponseVO.buildSuccess(userService.getUserFavorite(uid));
    }

    @UserLoginToken
    @GetMapping("/getUserInfo")
    public ResponseVO getUserInfo(@RequestParam long uid){
        return ResponseVO.buildSuccess(userService.getUserInfo(uid));
    }

    @UserLoginToken
    @PostMapping("/modifyUserInfo")
    public ResponseVO modifyUserInfo(@RequestParam long uid,@RequestParam String nickname, @RequestParam String avatar){
        if(userService.modifyUserInfo(uid,nickname,avatar)){
            return ResponseVO.buildSuccess("修改用户信息成功");
        }else {
            return ResponseVO.buildFailure("修改用户信息失败");
        }
    }

    @UserLoginToken
    @PostMapping("/modifyPassword")
    public ResponseVO modifyPassword(@RequestParam long uid,@RequestParam String pwd){
        String token = userService.modifyPassword(uid,pwd);
        if(!token.equals("")){
            return ResponseVO.buildSuccess(token);
        }else {
            return ResponseVO.buildFailure("修改用户密码失败");
        }
    }

    /**
     *检查Email 格式（正则表达式）
     * @param email email
     * @return true or false
     */
    private boolean checkEmailFormat(String email){
        String REGEX="^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
        Pattern p = Pattern.compile(REGEX);
        Matcher matcher=p.matcher(email);
        return matcher.matches();
    }

}
