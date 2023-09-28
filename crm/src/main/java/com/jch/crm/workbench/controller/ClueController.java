package com.jch.crm.workbench.controller;

import com.jch.crm.settings.commons.contants.Contants;
import com.jch.crm.settings.commons.domain.ReturnObject;
import com.jch.crm.settings.commons.utils.DateUtils;
import com.jch.crm.settings.commons.utils.UUIDUtils;
import com.jch.crm.settings.domain.DicValue;
import com.jch.crm.settings.domain.User;
import com.jch.crm.settings.service.DicValueService;
import com.jch.crm.settings.service.UserService;
import com.jch.crm.workbench.domain.Activity;
import com.jch.crm.workbench.domain.Clue;
import com.jch.crm.workbench.domain.ClueActivityRelation;
import com.jch.crm.workbench.domain.ClueRemark;
import com.jch.crm.workbench.service.ActivityService;
import com.jch.crm.workbench.service.ClueActivityRelationService;
import com.jch.crm.workbench.service.ClueRemarkService;
import com.jch.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @Autowired
    private ActivityService activityService;

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
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
            returnObject.setData(clueList);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
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
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
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
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("更新失败, 请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
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

    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id, HttpServletRequest request) {
        Clue clue = clueService.queryClueDetailById(id);
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkListByClueId(id);
        List<Activity> activityList = activityService.queryActivityDetailByClueId(clue.getId());
        request.setAttribute("clue", clue);
        request.setAttribute("clueRemarkList", clueRemarkList);
        request.setAttribute("activityList", activityList);
        return "workbench/clue/detail";
    }


    @RequestMapping("/workbench/clue/queryActivityDetailByNameClueId.do")
    @ResponseBody
    public Object queryActivityDetailByNameClueId(String activityName, String clueId) {

        // 封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);

        List<Activity> activityList = activityService.queryActivityDetailByNameClueId(map);
        return activityList;
    }

    @RequestMapping("/workbench/clue/saveBound.do")
    @ResponseBody
    public Object saveBound(String[] activityId, String clueId) {
        //返回结果
        ReturnObject returnObject = new ReturnObject();
        // 封装参数
        List<ClueActivityRelation> list = new ArrayList<>();
        ClueActivityRelation clueActivityRelation = null;
        for (String s : activityId) {
            clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtils.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(s);
            list.add(clueActivityRelation);
        }
        try {
            int count = clueActivityRelationService.saveClueActivityRelationBatch(list);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
                List<Activity> activityList = activityService.queryActivityDetailByIds(activityId);
                returnObject.setData(activityList);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("插入失败,请稍后重试。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("插入失败,请稍后重试。");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/saveUnBound.do")
    @ResponseBody
    public Object saveUnBound(String clueId, String activityId) {
        //返回结果
        ReturnObject returnObject = new ReturnObject();
        // 收集参数
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(clueId);
        clueActivityRelation.setActivityId(activityId);
        try {
            int count = clueActivityRelationService.deleterClueActivityRelationByCondition(clueActivityRelation);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("删除失败,请稍后重试。");
            }
        } catch (Exception e) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败,请稍后重试。");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/toConvert.do")
    public String toConvert(String id, HttpServletRequest request) {
        // 查询线索明细信息
        Clue clue = clueService.queryClueDetailById(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");

        request.setAttribute("clue", clue);
        request.setAttribute("stageList", stageList);
        // 请求转发
        return "workbench/clue/convert";
    }

    @RequestMapping("/workbench/clue/queryActivityConvertByNameClueId.do")
    @ResponseBody
    public Object queryActivityConvertByNameClueId(String activityName, String clueId) {
        // 封装参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        List<Activity> activityList = activityService.queryActivityConvertByNameClueId(map);
        return activityList;
    }

    @RequestMapping("/workbench/clue/convertClue.do")
    @ResponseBody
    public Object convertClue(String clueId, String money, String name, String expectedDate, String stage, String activityId,
                              String isCreateTran, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        // 封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("clueId", clueId);
        map.put("money", money);
        map.put("name", name);
        map.put("expectedDate", expectedDate);
        map.put("stage", stage);
        map.put("activityId", activityId);
        map.put("isCreateTran", isCreateTran);
        map.put(Contants.SESSION_USER, user);
        try {
            clueService.saveConvert(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试...");
        }
        return returnObject;
    }
}
