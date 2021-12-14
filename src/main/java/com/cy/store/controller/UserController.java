package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.util.JsonResult;
import com.cy.store.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        User byUid = iUserService.getByUid(user.getUid());
        return new JsonResult<>(OK,byUid);

    }

    @PostMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session){
        User result = (User) session.getAttribute("user");
        iUserService.changeInfo(result.getUid(),result.getUsername(),user);
        return new JsonResult<>(OK);
    }

    // 定义文件上传的最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    // 限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");

    }
    @PostMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file){


        // 判断文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出限制");
        }
        // 判断文件的类型是否是我们规定的后缀类型
        String contentType = file.getContentType();
        // 如果集合中包含某个文件则返回值为true
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }

        // 文件保存的路径：../upload/文件名
        String parent = session.getServletContext().getRealPath("upload");
        // File对象指向这个路径
        File dir = new File(parent);
        if (!dir.exists()){
            dir.mkdirs(); // 创建目录
        }
        // 获取道这个文件名称，使用UUID工具将生成的一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();  // 只拿到文件名和文件或后缀，例如 avatar.jpg
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;

        // 创建一个空文件
        File dest = new File(dir,filename);
        // 将参数file中数据写入到这个空文件中
        try {
            file.transferTo(dest);  // 将file文件中的数据写入到dest文件中
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        // 修改头像
        User user = (User) session.getAttribute("user");
        String avatar = "/upload/" + filename;
        iUserService.changeAvatar(user.getUid(),avatar,user.getUsername());
        // 返回用户头像的路径给前端页面，将来用于头像展示使用
        return new JsonResult<>(OK,avatar);
    }
}
