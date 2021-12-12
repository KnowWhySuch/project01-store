package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;

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
}
