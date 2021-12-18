package com.cy.store.service;

import com.cy.store.entity.District;
import com.cy.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Service层测试
 */
@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private IProductService service;

    @Test
    public void findHotListTest(){
        List<Product> hotList = service.findHotList();
        System.out.println(hotList);
    }

    @Test
    public void findByIdTest(){
        Product byId = service.findById(10000001);
        System.out.println(byId);
    }
}
