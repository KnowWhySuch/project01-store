package com.cy.store.mapper;

import com.cy.store.entity.Address;

/**
 *
 */
public interface AddressMapper {

    /**
     * 插入用户的收货地址数据
     * @param address
     * @return
     */
    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址的数量
     * @param uid 用户的id
     * @return 当前用户的收货地址总数
     */
    Integer countById(Integer uid);
}
