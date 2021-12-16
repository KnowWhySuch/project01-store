package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IDistrictService iDistrictService;

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
        // 对address对象中的数据进行补全：省市区
        String provinceName = iDistrictService.getNameByCode(address.getProvinceCode());
        String cityName = iDistrictService.getNameByCode(address.getCityCode());
        String areaName = iDistrictService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

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

    /**
     * 根据用户id获取用户地址信息
     * @param uid 用户id
     * @return
     */
    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
            address.setAreaCode(null);
            address.setCityCode(null);
            address.setProvinceCode(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setIsDefault(null);
            address.setTel(null);
        }

        return list;
    }

    /**
     * 修改某个用户的某条收货地址数据为默认收货地址
     * @param aid 收货地址的id
     * @param uid 用户的id
     * @param username 修改人名字
     */
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("收货地址不存在");
        }
        // 检测当前获取到的收货地址数据的归属
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }

        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1){
            throw new UpdateException("更新数据产生未知的异常");
        }
        // 将用户选中的某条地址设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1){
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    /**
     * 删除用户选中的收货地址数据
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 修改人名字
     */
    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }

        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1){
            throw new DeleteException("删除数据产生的异常");
        }

        Integer count = addressMapper.countById(uid);
        if (count == 0){
            return;
        }

        if (address.getIsDefault() == 0){
           return;
        }
        // 将这条数据设置为默认
        Address lastModified = addressMapper.findLastModified(uid);
        rows = addressMapper.updateDefaultByAid(lastModified.getAid(), username, new Date());

        if (rows != 1){
            throw new DeleteException("更新数据时产生未知的异常");
        }

    }
}
