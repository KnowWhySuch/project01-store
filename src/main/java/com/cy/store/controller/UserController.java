package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.util.JsonResult;
import com.cy.store.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@Slf4j
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
        System.out.println(user.getUid());
        return new JsonResult<>(OK);
    }

    @PostMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User user = iUserService.login(username, password);
        //  保存用户信息到session
        session.setAttribute("user",user);
        return new JsonResult<>(OK,user);
    }

    @PostMapping("/change_password")
    public JsonResult<Void>  changePassword(String oldPassword,String newPassword,HttpSession session){
        User user = (User) session.getAttribute("user");
        iUserService.changePassword(user.getUid(),user.getUsername(),oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @GetMapping("/get_user_info")
    public JsonResult<User> getByUid(HttpSession session){
        User user = (User) session.getAttribute("user");
        return new JsonResult<>(OK,user);

    }

    @PostMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session){

        return new JsonResult<>(OK);
    }
}
