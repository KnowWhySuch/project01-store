package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.PasswordNotMatchException;
import com.cy.store.service.ex.UserNotFoundException;
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

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 当前匹配的用户数据，如果没有返回null
     */
    @Override
    public User login(String username, String password) {
        // 查询用户名是否存在
        User user = userMapper.findByUsername(username);
        if (user == null){
            throw new UserNotFoundException("用户名不存在");
        }
        // 检测密码是否正确
        // 1.获取到数据库中加密之后的密码
        String pwd = user.getPassword();
        // 2.获取数据库中的盐值
        String salt = user.getSalt();
        // 3.使用md5加密密码
        String md5Password = getMD5Password(password, salt);
        // 判断密码是否正确
        if (!pwd.equals(md5Password)){
            throw new PasswordNotMatchException("密码错误");
        }

        // 判断is_delete字段的值是否为1，表示被删除
        if (user.getIsDelete() == 1){
            throw new UserNotFoundException("用户已经被删除");
        }

        // 提升系统的性能
        User result = new User();
        result.setUsername(user.getUsername());
        result.setPassword(user.getPassword());
        result.setAvatar(user.getAvatar());

        return result;

    }

    /** 定义一个md5算法的加密处理 **/
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password +salt).getBytes()).toUpperCase();
        }
       return password;
    }
}
