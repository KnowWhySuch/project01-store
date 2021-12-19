package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

/**
 * 收货地址的接口
 */
public interface IAddressService {

    /**
     * 新增收货地址
     * @param uid 用户id
     * @param username 用户名
     * @param address 新增地址
     */
    void addNewAddress(Integer uid,String username ,Address address);

    /**
     * 根据用户id获取用户地址信息
     * @param uid 用户id
     * @return 地址信息
     */
    List<Address> getByUid(Integer uid);


    /**
     *  修改某个用户的某条收货地址数据为默认收货地址
     * @param aid 收货地址的id
     * @param uid 用户的id
     * @param username 修改人名字
     */
    void setDefault(Integer aid,Integer uid,String username);


    /**
     * 删除用户选中的收货地址数据
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 修改人名字
     */
    void delete(Integer aid, Integer uid, String username);


    /**
     * 根据aid查询地址信息
     * @param aid 地址的aid
     * @return 地址信息
     */
    Address getByAid(Integer aid,Integer uid);

}
