package com.cy.store.service;

import com.cy.store.entity.User;

import javax.servlet.http.HttpSession;

/**
 * 用户模块业务层接口
 */
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void  reg(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 当前匹配的用户数据，如果没有返回null
     */
    User login(String username,String password);


    /**
     * 修改密码
     * @param uid 用户id
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid, String username ,String oldPassword,String newPassword);


    /**
     * 根据用户的uid获取用户的数据
     * @param uid 用户id
     * @return 用户的数据
     */
    User getByUid(Integer uid);


    /**
     * 修改用户信息
     * @param uid 用户id
     * @param username 用户名
     * @param user 用户填写的信息
     */
    void changeInfo(Integer uid , String username ,User user );
}
