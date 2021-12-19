package com.cy.store.controller;

import com.cy.store.entity.Cart;
import com.cy.store.entity.User;
import com.cy.store.service.ICartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/carts")
public class CartController extends BaseController {

    @Autowired
    private ICartService iCartService;

    @GetMapping("/add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session ){
        User user = (User) session.getAttribute("user");
        iCartService.addToCart(user.getUid(),pid,amount,user.getUsername());
        return new JsonResult<>(OK);
    }

    @GetMapping({"","/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<CartVO> data = iCartService.getVOByUid(user.getUid());
        return new JsonResult<>(OK,data);
    }

    @GetMapping ("/{cid}/num/add")
    public JsonResult<Integer> addNum(Integer cid,HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer data = iCartService.addNum(cid, user.getUid(), user.getUsername());
        return new JsonResult<>(OK,data);
    }

    @GetMapping ("/{cid}/num/reduce")
    public JsonResult<Integer> reduceNum(Integer cid,HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer data = iCartService.reduceNum(cid, user.getUid(), user.getUsername());
        return new JsonResult<>(OK,data);
    }

    @GetMapping("/list")
    public JsonResult<List<CartVO>> getVOByCid(HttpSession session,Integer[] cids){
        User user = (User) session.getAttribute("user");
        List<CartVO> data = iCartService.getVOByCid(user.getUid(), cids);
        return new JsonResult<>(OK,data);
    }
}
