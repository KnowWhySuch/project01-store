package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
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
        System.out.println(user);
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
        return user;

    }

    /**
     * 修改密码
     * @param uid 用户id
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw  new UserNotFoundException("用户数据不存在");
        }
        // 原始密码和数据库中密码进行比较
        String password = result.getPassword();
        // 将用户输入的旧密码进行加密
        String md5Password = getMD5Password(oldPassword, result.getSalt());
        if (!password.equals(md5Password)){
            throw new PasswordNotMatchException("原密码错误");
        }

        // 将用户新密码加密，并加入到数据库中
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer integer = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (integer != 1){
            throw new UpdateException("更新时发生了未知的异常");
        }
    }

//    /**
//     * 根据用户的uid获取用户的数据
//     * @param uid 用户id
//     * @return
//     */
//    @Override
//    public User getByUid(Integer uid) {
//        User user = userMapper.findByUid(uid);
//        if (user == null || user.getIsDelete() == 1 ){
//            throw  new UserNotFoundException("用户数据不存在");
//        }
//        User result = new User();
//        result.setUid(user.getUid());
//        result.setUsername(user.getUsername());
//        result.setEmail(user.getEmail());
//        result.setGender(user.getGender());
//        result.setPhone(user.getPhone());
//        return result;
//    }


    /**
     * 更改用户信息
     * @param uid 用户id
     * @param username 用户名
     * @param user 用户填写的信息
     */
    @Override
    public void changeInfo(Integer uid , String username ,User user ) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1 ){
            throw  new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1){
            throw new UpdateException("更新数据出现了异常");
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
