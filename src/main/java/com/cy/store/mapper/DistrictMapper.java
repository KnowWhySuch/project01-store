package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

/**
 *
 */
public interface DistrictMapper {

    /**
     * 根据父代号查询区域信息
     * @param parent
     * @return
     */
    List<District> findByParent(String parent);


    String findNameByCode(String code);
}
