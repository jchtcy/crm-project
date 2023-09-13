package com.jch.crm.workbench.controller;

import com.jch.crm.commons.contants.Contants;
import com.jch.crm.commons.domain.ReturnObject;
import com.jch.crm.commons.utils.DateUtils;
import com.jch.crm.commons.utils.UUIDUtils;
import com.jch.crm.settings.domain.User;
import com.jch.crm.settings.service.UserService;
import com.jch.crm.workbench.domain.Activity;
import com.jch.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request) {
        List<User> userList = userService.listAllUsers();
        request.setAttribute("userList", userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session) {
        User user = (User)session.getAttribute(Contants.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int count = activityService.saveCreateActivity(activity);

            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
                returnObject.setMessage("系统忙,请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试...");
        }
        return returnObject;
    }
}
