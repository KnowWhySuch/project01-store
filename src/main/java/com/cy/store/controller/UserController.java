package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.util.JsonResult;
import com.cy.store.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController{
    @Autowired
    private IUserService iUserService;

    @PostMapping("/reg")
    public JsonResult<Void> reg(User user){

        // 方法1：
        // 创建响应结果对象
//        JsonResult<Void> result = new JsonResult<>();
//        try {
//            iUserService.reg(user);
//            result.setState(200);
//            result.setMessage("用户注册成功");
//        }catch (UsernameDuplicatedException e){
//            result.setState(4000);
//            result.setMessage("用户名被占用");
//        }catch (InsertException e){
//            result.setState(5000);
//            result.setMessage("注册时产生未知的异常");
//        }
//        return result;

//        优化：
        iUserService.reg(user);
        return new JsonResult<>(OK);
    }

    @PostMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User user = iUserService.login(username, password);
        //  保存用户信息到session
        session.setAttribute("user",user);
        return new JsonResult<>(OK,user);
    }

}
