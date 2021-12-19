package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ICartService;
import com.cy.store.service.IOrderService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.vo.CartVO;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    private IAddressService iAddressService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ICartService iCartService;

    @Override
    public Order create(Integer aid, Integer uid, String username, Integer[] cids) {
        // 即将要下单的列表
        List<CartVO> list = iCartService.getVOByCid(uid, cids);
        // 计算商品的总价
        Long totalPrice = 0L;

        for (CartVO cartVO : list) {
           totalPrice += cartVO.getPrice() * cartVO.getNum();


        }
        Address address = iAddressService.getByAid(aid, uid);
        // 插入数据
        Order order = new Order();
        order.setUid(uid);
        // 收货地址信息
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        // 支付、总价
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        // 日志信息
        order.setCreatedTime(new Date());
        order.setCreatedUser(username);
        order.setModifiedTime(new Date());
        order.setModifiedUser(username);

        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1){
            throw new InsertException("插入时发生异常");
        }

        for (CartVO cart : list) {
            // 创建订单商品数据
            OrderItem item = new OrderItem();
            // 补全数据：setOid(order.getOid())
            item.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            item.setPid(cart.getPid());
            item.setTitle(cart.getTitle());
            item.setImage(cart.getImage());
            item.setPrice(cart.getRealPrice());
            item.setNum(cart.getNum());
            // 补全数据：4项日志
            item.setCreatedUser(username);
            item.setCreatedTime(new Date());
            item.setModifiedUser(username);
            item.setModifiedTime(new Date());
            // 插入订单商品数据
            Integer rows2 = orderMapper.insertOrderItem(item);
            if (rows2 != 1) {
                throw new InsertException("插入订单商品数据时出现未知错误，请联系系统管理员");
            }
        }


        return order;


    }
}
