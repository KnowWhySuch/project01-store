package com.cy.store.mapper;

import com.cy.store.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


/**
 *
 */
// @SpringBootTest：表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest

public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
    }

    @Test
    public void findByUsername(){

        User user = userMapper.findByUsername("test");

        System.out.println(user);
        System.out.println(user.getCreatedUser());
        System.out.println(user.getCreatedTime());
        System.out.println(user.getModifiedUser());
        System.out.println(user.getModifiedTime());

    }

    @Test
    public void updatePasswordByUidTest(){
        userMapper.updatePasswordByUid(12,"1234","管理员",new Date());
    }

    @Test
    public void findByUidTest(){
        User user = userMapper.findByUid(19);

    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(17);
        user.setPhone("17677351612");
        user.setEmail("644452492@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
}
