package com.example.hcibackend.controller;

import com.example.hcibackend.po.LoginForm;
import com.example.hcibackend.po.RegisterForm;
import com.example.hcibackend.service.UserService;
import com.example.hcibackend.vo.ResponseVO;
import com.example.hcibackend.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController()
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseVO login(@RequestBody LoginForm loginForm){
        UserVO user = userService.login(loginForm);
        if(user!=null){
            return ResponseVO.buildSuccess(user);
        }else {
            return ResponseVO.buildFailure("用户名或密码错误！");
        }

    }

    @GetMapping(value = "/code")
    public ResponseVO sendCode(@RequestParam String email){

        if(!checkEmailFormat(email)){
            return ResponseVO.buildFailure("邮箱格式不正确");
        }else {
            userService.sendCode(email);
            return ResponseVO.buildSuccess("验证码已发送");
        }
    }

    @PostMapping(value = "/register")
    public ResponseVO register(@RequestBody RegisterForm registerForm){
        int status = userService.checkCode(registerForm.getEmail(),registerForm.getCode());
        if(status==0){
            UserVO user = userService.register(registerForm);
            return ResponseVO.buildSuccess(user);
        }else if(status==1){
            return ResponseVO.buildFailure("验证码错误");
        }else{
            return ResponseVO.buildFailure("验证码超时");
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
