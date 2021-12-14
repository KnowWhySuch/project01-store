package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块得持久层接口
 */
//@Mapper
public interface UserMapper {
    /***
     * 插入用户得数据
     * @param user 用户的数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /***
     * 根据用户名来查询用户的数据
     * @param username 用户名
     * @return 如果找到对应的user返回user数据，反之，返回null
     */
    User findByUsername(String username);

    /**
     * 根据用户的uid来修改用户密码
     * @param uid 用户的id
     * @param password 用户输入的新密码
     * @param modifiedUser 修改的执行者
     * @param modifiedTime 修改数据的时间
     * @return 返回值为受影响的行数
     */
    Integer updatePasswordByUid( Integer uid,String password,String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id查询用户的数据
     * @param uid 用户的id
     * @return 如果找到则返回对象，反之返回null
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据信息
     * @param user 用户的数据
     * @return 返回受影响的行数
     */
    Integer updateInfoByUid(User user);


    /**
     * 根据用户uid值来修改用户的头像
     * @param id
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
