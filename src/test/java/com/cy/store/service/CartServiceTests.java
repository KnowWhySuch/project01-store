package com.cy.store.service;

import com.cy.store.entity.Product;
import com.cy.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * Service层测试
 */
@SpringBootTest
public class CartServiceTests {

    @Autowired
    private ICartService service;

    @Test
    public void addToCart(){
       service.addToCart(17,10000002,-10,"女朋友");
    }

    @Test
    public void getVOByUid(){
        System.out.println(service.getVOByUid(17));
    }

    @Test
    public void addNum(){
        Integer rows = service.addNum(1, 17, "张三");
        System.out.println(rows);
    }

    @Test
    public void reduceNum(){
        Integer rows = service.reduceNum(1, 17, "张三");
        System.out.println(rows);
    }

    @Test
    public void getVOByCid(){
        Integer[] cids = {1, 2, 34, 5};
        List<CartVO> voByCid = service.getVOByCid(17, cids);
        System.out.println(voByCid);
    }

}
