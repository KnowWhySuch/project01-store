package com.cy.store.mapper;

import com.cy.store.entity.District;
import com.cy.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


/**
 *
 */
// @SpringBootTest：表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest

public class ProductMapperTests {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void findHotList(){
        List<Product> hotList = productMapper.findHotList();
        System.out.println(hotList);
    }


    @Test
    public void findById(){
        Product byId = productMapper.findById(10000001);
        System.out.println(byId);
    }



}
