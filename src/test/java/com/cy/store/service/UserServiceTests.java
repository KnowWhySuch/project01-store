package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * Service层测试
 */
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private IUserService iUserService;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("test");
            user.setPassword("123");
            iUserService.reg(user);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
            System.out.println(e.getClass().getSimpleName());
        }

    }

    @Test
    public void login(){
        User test01 = iUserService.login("test", "123");
        System.out.println(test01);
    }


    @Test
    public void changgePassword(){
        iUserService.changePassword(14,"xxx","123","6666");
    }


    @Test
    public void getByUidTest(){
        System.out.println(iUserService.getByUid(19));

    }

    @Test
    public void changeInfoTest(){
        User user = new User();
        user.setPhone("188888888");
        user.setGender(1);
        user.setEmail("168@qq.com");
        iUserService.changeInfo(19,"test",user);
    }

    @Test
    public void changeAvatar(){
        iUserService.changeAvatar(19,"/ss//dd","test");
    }
}
