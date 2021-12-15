package com.cy.store.service;

import com.cy.store.entity.Address;

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
}
