package com.cy.store.vo;

import lombok.Data;

/**
 * 该类表示购物车数据的VO类（Value Object）
 */
@Data
public class CartVO {

    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private Long realPrice;
    private String image;
}
