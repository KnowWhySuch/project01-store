package com.cy.store.service;

import com.cy.store.entity.Order;
import com.cy.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Service层测试
 */
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private IOrderService service;

    @Test
    public void create(){
        Integer[] cids = {3,4};
        Order order = service.create(13, 17, "张三", cids);
        System.out.println(order);
    }

}
