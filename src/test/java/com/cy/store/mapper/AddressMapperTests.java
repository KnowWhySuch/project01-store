package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


/**
 *
 */
// @SpringBootTest：表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest

public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(17);
        Integer insert = addressMapper.insert(address);
        System.out.println(insert);
    }

    @Test
    public void countById(){
        Integer count = addressMapper.countById(17);
        System.out.println(count);
    }


}
