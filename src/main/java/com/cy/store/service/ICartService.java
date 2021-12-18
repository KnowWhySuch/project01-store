package com.cy.store.service;

import com.cy.store.vo.CartVO;

import java.util.List;

/**
 *
 */
public interface ICartService {

    /**
     * 将商品加入购物车
     * @param uid 用户id
     * @param pid 商品id
     * @param amount 新增数量
     * @param username 用户名（修改者）
     */
    void addToCart(Integer uid,Integer pid, Integer amount,String username);

    /**
     * 根据用户id查询用户的购物车信息
     * @param uid 用户id
     * @return 购物车信息
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 更新用户购物车数据的数量
     * @param cid
     * @param uid
     * @param username
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid,Integer uid,String username);

    /**
     * 更新用户购物车数据的数量
     * @param cid
     * @param uid
     * @param username
     * @return 减少成功后新的数量
     */
    Integer reduceNum(Integer cid,Integer uid , String username);



    List<CartVO> getVOByCid(Integer uid, Integer[] cids);
}
