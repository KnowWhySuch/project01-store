package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    // 收货地址限制最大条数
    @Value("${user.address.max-count}")
    private Integer maxCount;

    /**
     * 新增收货地址
     * @param uid 用户id
     * @param username 用户名
     * @param address 新增地址
     */
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        // 先判断用户的收货地址是否为20条或0条
        Integer count = addressMapper.countById(uid);
        if (count >= maxCount){
            throw new AddressCountLimitException("用户收货地址超出上限");
        }
        // 0条收货地址时，设置默认
        Integer isDefault = count == 0 ? 1 : 0;  // 1表示默认，0表示不默认
        address.setIsDefault(isDefault);
        address.setUid(uid);
        // 补全日志信息
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        // 插入数据
        Integer rows = addressMapper.insert(address);
        if (rows != 1){
            throw new InsertException("添加收货地址时放生了异常");
        }
    }
}
