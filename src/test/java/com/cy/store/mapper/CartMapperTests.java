package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
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

public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setUid(17);
        cart.setPid(10000001);
        cart.setNum(2);
        cart.setPrice(23L);
       cartMapper.insert(cart);
    }


    @Test
    public void updateNumByCid(){
        cartMapper.updateNumByCid(1,4,"zhangsan",new Date());
    }

    @Test
    public void findByUidAndPid(){
        Cart byUidAndPid = cartMapper.findByUidAndPid(17, 10000001);
        System.out.println(byUidAndPid);
    }


    @Test
    public void findVOByUid(){
        System.out.println(cartMapper.findVOByUid(17));
    }

    @Test
    public void findByCidAfter(){
        System.out.println(cartMapper.findByCid(1));
    }


    @Test
    public void findVOByCid(){
        Integer[] cids = {1,2,3,4,5,6};
        System.out.println(cartMapper.findVOByCid(cids));
    }

}
