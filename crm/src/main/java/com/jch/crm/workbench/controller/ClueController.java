package com.jch.crm.workbench.controller;

import com.jch.crm.settings.commons.contants.Contants;
import com.jch.crm.settings.commons.domain.ReturnObject;
import com.jch.crm.settings.commons.utils.DateUtils;
import com.jch.crm.settings.commons.utils.UUIDUtils;
import com.jch.crm.settings.domain.DicValue;
import com.jch.crm.settings.domain.User;
import com.jch.crm.settings.service.DicValueService;
import com.jch.crm.settings.service.UserService;
import com.jch.crm.workbench.domain.Clue;
import com.jch.crm.workbench.domain.ClueRemark;
import com.jch.crm.workbench.service.ClueRemarkService;
import com.jch.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ClueController {

    @Autowired
    private UserService userService;

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private ClueService clueService;

    @Autowired
    private ClueRemarkService clueRemarkService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request) {
        List<User> userList = userService.listAllUsers();
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("userList", userList);
        request.setAttribute("appellationList", appellationList);
        request.setAttribute("clueStateList", clueStateList);
        request.setAttribute("sourceList", sourceList);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/getClueListByCondition.do")
    @ResponseBody
    public Object getClueListByCondition(Clue clue) {
        ReturnObject returnObject = new ReturnObject();
        try {
            List<Clue> clueList = clueService.queryClueListByCondition(new Clue());
            returnObject.setCode(Contants.RETURN_OBJECT_DOCE_SUCESS);
            returnObject.setData(clueList);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
            returnObject.setMessage("查询失败, 请稍后重试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    @ResponseBody
    public Object saveCreateClue(Clue clue, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formateDateTime(new Date()));
        try {
            int count = clueService.saveCreateClue(clue);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
                returnObject.setMessage("插入失败, 请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
            returnObject.setMessage("插入失败, 请稍后重试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/getClueById.do")
    @ResponseBody
    public Object getClueById(String id) {
        return clueService.queryClueById(id);
    }

    @RequestMapping("/workbench/clue/saveEditClue.do")
    @ResponseBody
    public Object saveEditClue(Clue clue, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        clue.setEditBy(user.getId());
        clue.setEditTime(DateUtils.formateDateTime(new Date()));
        try {
            int count = clueService.saveEditClue(clue);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
                returnObject.setMessage("更新失败, 请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
            returnObject.setMessage("更新失败, 请稍后重试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/deleteClueByIds.do")
    @ResponseBody
    public Object deleteClueByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int count = clueService.deleteClueByIds(id);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
                returnObject.setMessage("删除失败, 请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_DOCE_FAIL);
            returnObject.setMessage("删除失败, 请稍后重试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id, HttpServletRequest request) {
        Clue clue = clueService.queryClueById(id);
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkListByClueId(id);

        request.setAttribute("clue", clue);
        request.setAttribute("clueRemarkList", clueRemarkList);
        return "workbench/clue/detail";
    }
}
