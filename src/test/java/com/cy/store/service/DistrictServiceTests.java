package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Service层测试
 */
@SpringBootTest
public class DistrictServiceTests {

    @Autowired
    private IDistrictService service;

    @Test
    public void getByParent(){
        // 86表示中国，所有的省的父代号都是86
        List<District> parent = service.getByParent("86");
        for (District district : parent) {
            System.out.println(district);
        }
    }
}
