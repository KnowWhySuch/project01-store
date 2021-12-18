package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CatNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.CardNotPresentException;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 将商品加入购物车
     * @param uid 用户id
     * @param pid 商品id
     * @param amount 新增数量
     * @param username 用户名（修改者）
     */
    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {

        // 查询当前要添加的这个商品是否已经在购物车
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if (result == null){
            Cart cart = new Cart();
            // 表示这个商品从来没加到该用户的购物车
            // 查出用户要加入购物车商品的信息
            Product product = productMapper.findById(pid);
            cart.setUid(uid);
            cart.setPid(product.getId());
            cart.setNum(amount);
            cart.setPrice(product.getPrice());
            cart.setCreatedTime(new Date());
            cart.setCreatedUser(username);
            cart.setModifiedTime(new Date());
            cart.setModifiedUser(username);
            Integer rows = cartMapper.insert(cart);
            if (rows != 1){
                throw new InsertException("插入时发生异常");
            }

        }else{
            // 表示当前购物车有该商品，更新它的数量即可
            Integer rows = cartMapper.updateNumByCid(result.getCid(),result.getNum() + amount, username, new Date());
            if (rows != 1){
                throw new UpdateException("更新时发生异常");
            }
        }
    }

    /**
     * 根据用户id查询用户的购物车信息
     * @param uid 用户id
     * @return 购物车信息
     */
    @Override
    public List<CartVO> getVOByUid(Integer uid) {
       return cartMapper.findVOByUid(uid);
    }

    /**
     * 更新用户购物车数据的数量
     * @param cid
     * @param uid
     * @param username
     * @return
     */
    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null){
            throw  new CatNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num , username, new Date());
        if (rows != 1){
            throw new UpdateException("更新时放生异常");
        }
        // 返回新的购物车数据的总量
        return num;

    }

    /**
     * 更新用户购物车数据的数量
     * @param cid
     * @param uid
     * @param username
     * @return 减少成功后新的数量
     */
    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null){
            throw  new CatNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() - 1;
        Integer rows = cartMapper.updateNumByCid(cid, num , username, new Date());
        if (rows != 1){
            throw new UpdateException("更新时放生异常");
        }
        // 返回新的购物车数据的总量
        return num;
    }

    @Override
    public List<CartVO> getVOByCid(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCid(cids);
        for (CartVO cartVO : list) {
            if (!cartVO.getUid().equals(uid)){  // 表示当前的数据不属于当前的用户
                // 从集合中移除这个元素
                list.remove(cartVO);
            }
        }
        return list;
    }
}
