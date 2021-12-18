package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface CartMapper {

    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 更新购物车的数据
     * @param cid 购物车id
     * @param num 商品数量
     * @param modifiedUser 更改的名字
     * @param modifiedTime 更改的时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户id和商品的id查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 购物车数据
     */
    Cart findByUidAndPid(Integer uid,Integer pid);

    /**
     * 根据用户id查询用户购物车的信息
     * @param uid
     * @return
     */
    List<CartVO> findVOByUid(Integer uid);

    /**
     * 根据cid购物车id查询购物车信息
     * @param cid 购物车id
     * @return 购物车信息
     */
    Cart findByCid(Integer cid);


    List<CartVO> findVOByCid(Integer[] cids);

}
