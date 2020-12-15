package com.example.hcibackend.service;

import com.example.hcibackend.entity.User;
import com.example.hcibackend.po.LoginForm;
import com.example.hcibackend.po.RegisterForm;
import com.example.hcibackend.vo.UserVO;

public interface UserService {
    /**
     * 发送验证码
     * @param email 邮箱
     */
    void sendCode(String email);

    /**
     * 判断验证码是否正确
     * @param email 邮箱地址
     * @param code 验证码
     * @return 0正确，1错误，2超时
     */
    int checkCode(String email, String code);

    /**
     * 注册用户
     * @param registerForm 用户信息
     * @return userVO
     */
    UserVO register(RegisterForm registerForm);

    /**
     * 用户登录
     * @param loginForm 登录信息
     * @return userVO
     */
    UserVO login(LoginForm loginForm);

    /**
     * 发送新密码
     * @param email 邮箱地址
     */
    void sendNewPwd(String email);

    /**
     * 改变收藏状态
     * @param uid 用户id
     * @param movieId 电影id
     * @return 是否成功
     */
    boolean changeFollow(String uid, String movieId);
}
