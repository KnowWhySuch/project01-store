package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


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

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(17);
        for (Address address : list) {
            System.out.println(address);
        }
    }

    @Test
    public void findByAid(){
        Address mapper = addressMapper.findByAid(9);
        System.out.println(mapper);
    }
    @Test
    public void updateNonDefault(){
        Integer rows = addressMapper.updateNonDefault(17);
        System.out.println(rows);
    }
    @Test
    public void updateDefaultByAid(){
        Integer rows = addressMapper.updateDefaultByAid(9, "管理员", new Date());
        System.out.println(rows);
    }

    @Test
    public void deleteByAid(){
        Integer rows = addressMapper.deleteByAid(10);
        System.out.println(rows);
    }

    @Test
    public void findLastModified(){
        Address address = addressMapper.findLastModified(17);
        System.out.println(address);
    }

}
