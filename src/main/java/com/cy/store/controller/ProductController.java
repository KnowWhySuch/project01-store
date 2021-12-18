package com.cy.store.controller;

import com.cy.store.entity.Product;
import com.cy.store.service.IProductService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/products")
public class ProductController extends BaseController{

    @Autowired
    private IProductService iProductService;

    @GetMapping("/hot_list")
    public JsonResult<List<Product>> getHotList(){
        List<Product> data = iProductService.findHotList();
        return new JsonResult<>(OK,data);
    }

    @GetMapping("/details")
    public JsonResult<Product> getById(Integer id){
        Product data = iProductService.findById(id);
        return new JsonResult<>(OK,data);
    }
}
