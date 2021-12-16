package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @GetMapping({"/",""})
    public JsonResult<List<Address>> getByUid(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Address> data = iAddressService.getByUid(user.getUid());
        return new JsonResult<>(OK,data);
    }

    @GetMapping("/{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session){
        User user = (User) session.getAttribute("user");
        iAddressService.setDefault(aid,user.getUid(),user.getUsername());
        return new JsonResult<>(OK);
    }

    @PostMapping("/{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid,HttpSession session){
        User user = (User) session.getAttribute("user");
        iAddressService.delete(aid,user.getUid(),user.getUsername());
        return new JsonResult<>(OK);
    }


}
