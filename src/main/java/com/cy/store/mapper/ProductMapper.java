package com.cy.store.mapper;

import com.cy.store.entity.Product;

import java.util.List;

/**
 *
 */
public interface ProductMapper {

    /**
     * 查询所有热销商品
     * @return 热销商品
     */
    List<Product> findHotList();

    /**
     * 商品详情页
     * @param id 商品id
     * @return 商品信息
     */
    Product findById(Integer id);
}
