package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 定义一拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 在调用所有请求方法之前执行
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url + Controller：映射）
     * @return true：放行请求； false：拦截请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user == null){
            // 说明用户没有登录，则重定向到login.html页面
            response.sendRedirect("/web/login.html");
            // 请求拦截
            return false;
        }
        // 请求放行
        return true;
    }

    /**
     * 在ModelAndView对象返回之后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求所有关联的资源被执行完毕之后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
