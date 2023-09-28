package com.jch.crm.workbench.controller;

import com.jch.crm.settings.commons.contants.Contants;
import com.jch.crm.settings.commons.domain.ReturnObject;
import com.jch.crm.settings.commons.utils.DateUtils;
import com.jch.crm.settings.commons.utils.UUIDUtils;
import com.jch.crm.settings.domain.User;
import com.jch.crm.workbench.domain.ActivityRemark;
import com.jch.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    @ResponseBody
    public Object saveCreateActivityRemark(ActivityRemark remark, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        remark.setId(UUIDUtils.getUUID());
        remark.setCreateTime(DateUtils.formateDateTime(new Date()));
        remark.setCreateBy(user.getId());
        remark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO_EDIT);

        try {
            int count = activityRemarkService.saveActivityRemarkForSelective(remark);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
                returnObject.setData(remark);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("插入失败, 请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("插入失败, 请稍后重试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/deleteActivityRemark.do")
    @ResponseBody
    public Object deleteActivityRemark(String id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int count = activityRemarkService.deleteActivityRemarkById(id);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("删除失败, 请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败, 请稍后重试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/updateActivityRemarkById.do")
    @ResponseBody
    public Object updateActivityRemarkById(ActivityRemark activityRemark, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_YES_EDIT);
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditTime(DateUtils.formateDateTime(new Date()));
        try {
            int count = activityRemarkService.updateActivityRemarkById(activityRemark);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
                returnObject.setData(activityRemark);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("更新评论失败, 请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("更新评论失败, 请稍后重试");
        }
        return returnObject;
    }
}
