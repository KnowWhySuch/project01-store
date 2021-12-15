package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 *
 */
@RestController
@RequestMapping("/addresses")
public class AddressContorller extends BaseController{
    @Autowired
    private IAddressService iAddressService;

    @PostMapping("/add_new_address")
    public JsonResult<Void> add_new_address(HttpSession session, Address address){
        User user = (User) session.getAttribute("user");
        iAddressService.addNewAddress(user.getUid(),user.getUsername(),address);
        return new JsonResult<>(OK);
    }
}
