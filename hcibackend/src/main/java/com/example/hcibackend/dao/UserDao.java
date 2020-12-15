package com.example.hcibackend.dao;

import com.example.hcibackend.entity.User;
import com.example.hcibackend.po.LoginForm;
import com.example.hcibackend.po.RegisterForm;
import com.example.hcibackend.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserDao {

    @Autowired
    private RedisService redisService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入用户
     * @param registerForm 用户信息
     */
    public String insertUser(RegisterForm registerForm) {
        long uid = 0L;
        String id = redisService.get("id");
        if (!StringUtils.isEmpty(id)) {
            uid = Long.parseLong(redisService.get("id"));
            uid++;
        }
        redisService.set("id",String.valueOf(uid));
        User user = new User();
        user.setUid(uid);
        user.setEmail(registerForm.getEmail());
        user.setNickname(registerForm.getNickname());
        user.setPassword(registerForm.getPassword());
        user.setAvatar("https://img.meituan.net/maoyanuser/c524afeb2e56c93093a1b7c26d5ac6b114424.png");
        mongoTemplate.insert(user,"user");

        return String.valueOf(uid);
    }

    /**
     * 用户登录
     * @param loginForm 登录信息
     * @return id
     */
    public User loginUser(LoginForm loginForm) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("email").is(username),Criteria.where("nickname").is(username));
        query.addCriteria(criteria);
        User user = mongoTemplate.findOne(query,User.class,"user");
        if(user!=null && user.getPassword().equals(password)){
            return user;
        }else {
            return null;
        }
    }

    /**
     * 根据id返回user
     * @param userId 用户id
     * @return user
     */
    public User findUserById(String userId) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(Long.parseLong(userId))),User.class,"user");
    }

    /**
     * 修改密码
     * @param pwd 新密码
     */
    public void updatePwd(String email, String pwd) {
        Update update = new Update();
        update.set("password",pwd);
        mongoTemplate.updateFirst(new Query(Criteria.where("email").is(email)),update,User.class,"user");
    }
}
