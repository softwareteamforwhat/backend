package com.example.hcibackend.serviceImp;

import com.example.hcibackend.dao.MovieDao;
import com.example.hcibackend.dao.UserDao;
import com.example.hcibackend.entity.User;
import com.example.hcibackend.po.LoginForm;
import com.example.hcibackend.po.RegisterForm;
import com.example.hcibackend.service.RedisService;
import com.example.hcibackend.service.TokenService;
import com.example.hcibackend.service.UserService;
import com.example.hcibackend.vo.MovieCollect;
import com.example.hcibackend.vo.UserInfo;
import com.example.hcibackend.vo.UserVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final RedisService redisService;

    private final TokenService tokenService;

    private final UserDao userDao;

    private final MovieDao movieDao;

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public UserServiceImp(RedisService redisService, TokenService tokenService, UserDao userDao, MovieDao movieDao) {
        this.redisService = redisService;
        this.tokenService = tokenService;
        this.userDao = userDao;
        this.movieDao = movieDao;
    }

    @Override
    public void sendCode(String email) {
        String code = createCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("邮箱登录验证码");
        message.setText(code);
        javaMailSender.send(message);
        redisService.set(email,code);
        redisService.expire(email,620);
    }

    @Override
    public int checkCode(String email, String code) {
        String redisCode = redisService.get(email);
        if(StringUtils.isEmpty(redisCode)){
            return 2;
        }else {
            if(redisCode.equals(code)){
                redisService.remove(email);
                return 0;
            }else {
                return 1;
            }
        }
    }

    @Override
    public UserVO register(RegisterForm registerForm) {
        String id = userDao.insertUser(registerForm);
        if(id.equals("用户已存在")){
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setAvatar("https://img.meituan.net/maoyanuser/c524afeb2e56c93093a1b7c26d5ac6b114424.png");
        userVO.setId(id);
        userVO.setToken(tokenService.getFirstToken(id,registerForm.getPassword()));
        return userVO;
    }

    @Override
    public UserVO login(LoginForm loginForm) {
        User user = userDao.loginUser(loginForm);
        if(user==null){
            return null;
        }else {
            UserVO userVO = new UserVO();
            userVO.setId(String.valueOf(user.getUid()));
            userVO.setToken(tokenService.getToken(user));
            userVO.setAvatar(user.getAvatar());
            return userVO;
        }
    }

    @Override
    public void sendNewPwd(String email) {
        String pwd = createCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("邮箱登录验证码");
        message.setText(pwd);
        userDao.updatePwd(email,pwd);
        javaMailSender.send(message);
    }

    @Override
    public boolean changeFollow(long uid, String movieId) {
        int state = movieDao.changeFollow(uid,movieId);
        return state == 0;
    }

    @Override
    public List<MovieCollect> getUserFavorite(long uid) {
        return userDao.getUserFavorite(uid);
    }

    @Override
    public UserInfo getUserInfo(long uid) {
        return userDao.getUserInfo(uid);
    }

    @Override
    public boolean modifyUserInfo(long uid, String nickname, String avatar) {
        return userDao.modifyUserInfo(uid,nickname,avatar);
    }

    @Override
    public String modifyPassword(long uid, String pwd) {
        return userDao.modifyPassword(uid,pwd);
    }

    @Override
    public boolean existEmail(String email) {
        return userDao.existEmail(email);
    }

    /**
     * 生成验证码
     * @return 验证码
     */
    private String createCode() {
        String[] letters = new String[] {
                "q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",
                "A","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M",
                "0","1","2","3","4","5","6","7","8","9"};
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(letters[(int) Math.floor(Math.random() * letters.length)]);
        }
        return code.toString();
    }
}
