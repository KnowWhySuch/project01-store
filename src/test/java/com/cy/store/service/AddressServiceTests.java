package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Service层测试
 */
@SpringBootTest
public class AddressServiceTests {

    @Autowired
    private IAddressService iAddressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("17677351612");
        address.setName("男朋友");
        iAddressService.addNewAddress(17,"管理员",address);
    }

    @Test
    public void getByUid(){
        List<Address> list = iAddressService.getByUid(17);
        for (Address address : list) {
            System.out.println(address);
        }
    }

    @Test
    public void setDefault(){
        iAddressService.setDefault(11,17,"老婆");
    }

    @Test
    public void delete(){
        iAddressService.delete(11,17,"管理员");
    }
}
