package com.jch.crm.settings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    /**
     * url要和controller方法处理完请求之后，响应信息返回的页面的资源目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }
}
