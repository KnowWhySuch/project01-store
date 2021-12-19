package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.entity.User;
import com.cy.store.service.IOrderService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 *
 */
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController{

    @Autowired
    private IOrderService service;

    @GetMapping("/create")
    public JsonResult<Order> create(HttpSession session,Integer aid, Integer[] cids){
        User user = (User) session.getAttribute("user");
        Order data = service.create(aid, user.getUid(), user.getUsername(), cids);
        return new JsonResult<>(OK,data);

    }
}
