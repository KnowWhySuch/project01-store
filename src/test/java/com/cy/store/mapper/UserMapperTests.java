package com.cy.store.mapper;

import com.cy.store.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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

        User user = userMapper.findByUsername("tim");
        System.out.println(user);

    }
}
