package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 *  用户模块业务层的实现类
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 判断用户名是否被注册了
        User result = userMapper.findByUsername(user.getUsername());
        // 判断结果集是否为null
        if (result != null){
            // 不为null，抛出用户名被占用的异常
            throw new UsernameDuplicatedException("用户名被占用");
        }
        // 密码加密处理:md5算法
        // md5: （串（盐值） + password + 串（盐值））
        // 获取用户名的密码
        String password = user.getPassword();
        // 获取盐值(随机生成一个盐值)
        String salt = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
        // 将密码和盐值作为一个整体进行加密处理
        String md5Password = getMD5Password(password, salt);
        // 将加密后的密码重新加入到user数据中，还要将盐值加入到user数据
        user.setPassword(md5Password);
        user.setSalt(salt);

        // 补全用户信息
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(date);
        // 执行注册逻辑
        Integer rows = userMapper.insert(user);
        if (rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }

    }

    /** 定义一个md5算法的加密处理 **/
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password +salt).getBytes()).toUpperCase();
        }
       return password;
    }
}
