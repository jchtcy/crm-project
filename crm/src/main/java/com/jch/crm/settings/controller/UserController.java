package com.jch.crm.settings.controller;

import com.jch.crm.commons.contants.Contants;
import com.jch.crm.commons.domain.ReturnObject;
import com.jch.crm.commons.utils.DateUtils;
import com.jch.crm.settings.domain.User;
import com.jch.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * url要和controller方法处理完请求之后，响应信息返回的页面的资源目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session){
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = userService.getUserByLoginActAndPwd(map);
        ReturnObject returnObject = new ReturnObject();
        if (user == null) {
            //登录失败,用户名或密码错误
            returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        } else {//判断账号是否合法
            String nowStr = DateUtils.formateDateTime(new Date());
            if (nowStr.compareTo(user.getExpireTime()) > 0) {
                //登录失败,账号已过期
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
                returnObject.setMessage("账号已过期");
            } else if ("0".equals(user.getLockState())) {
                //登录失败,状态已锁定
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
                returnObject.setMessage("状态已锁定");
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                //登录失败,ip受限
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
                returnObject.setMessage("ip受限");
            } else {
                //登录成功
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_SUCESS);

                //将user保存到session中
                session.setAttribute(Contants.SESSION_USER, user);
            }
        }
        return returnObject;
    }
}
