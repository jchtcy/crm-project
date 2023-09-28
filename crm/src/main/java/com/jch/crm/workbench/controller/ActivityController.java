package com.jch.crm.workbench.controller;

import com.jch.crm.settings.commons.contants.Contants;
import com.jch.crm.settings.commons.domain.ReturnObject;
import com.jch.crm.settings.commons.utils.DateUtils;
import com.jch.crm.settings.commons.utils.HSSFUtils;
import com.jch.crm.settings.commons.utils.UUIDUtils;
import com.jch.crm.settings.domain.User;
import com.jch.crm.settings.service.UserService;
import com.jch.crm.workbench.domain.ActivityRemark;
import com.jch.crm.workbench.domain.Activity;
import com.jch.crm.workbench.service.ActivityRemarkService;
import com.jch.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request) {
        List<User> userList = userService.listAllUsers();
        request.setAttribute("userList", userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int count = activityService.saveCreateActivity(activity);

            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙,请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/getActivityByConditionForPage.do")
    @ResponseBody
    public Object getActivityByConditionForPage(String name, String owner, String startDate, String endDate,
                                                int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("pageNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        List<Activity> activityList = activityService.listActivityByConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("activityList", activityList);
        resMap.put("totalRows", totalRows);
        return resMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivityByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        int row = 0;
        try {
            row = activityService.deleteActivityByIds(id);
            if (row > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙,请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    @ResponseBody
    public Object queryActivityById(String id) {
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    @ResponseBody
    private Object saveEditActivity(Activity activity, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();

        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setEditTime(DateUtils.formateDateTime(new Date()));
        activity.setEditBy(user.getId());

        try {
            int count = activityService.updateEditActivity(activity);
            if (count > 0 ) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙,请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    // testFileDownload
    @RequestMapping("/workbench/activity/fileDownload.do")
    public void fileDownload(HttpServletResponse response) {
        // 1、设置响应类型
        response.setContentType("application/octet-stream; charset=UTF-8");
        OutputStream out = null;
        FileInputStream fis = null;
        try {
            // 2、获取输出流
            out = response.getOutputStream();
            // 浏览器会默认打开响应信息,设置响应头信息,浏览器接收到响应信息之后不打开,直接下载文件
            response.addHeader("Content-Disposition", "attachment;filename=myStudentList.xls");
            fis = new FileInputStream("D:\\work\\serve\\studentList.xls");
            byte[] buff = new byte[256];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * testFileUpload
     * 配置springmvc的文件上传解析器
     */
    @RequestMapping("/workbench/activity/fileUpload.do")
    @ResponseBody
    public Object fileUpload (String username, MultipartFile myFile) throws IOException {
        System.out.println(username);
        // 把文件传到服务器
        // 路径不存在会报错, 文件可以不创建
        myFile.transferTo(new File("D:\\work\\serve\\" + myFile.getOriginalFilename()));

        // 返回响应信息
        ReturnObject returnObject = new ReturnObject();
        returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
        returnObject.setMessage("上传成功");
        return returnObject;
    }

    @RequestMapping("/workbench/activity/exportAllActivitys.do")
    public void exportAllActivitys(HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.queryAllActivitys();
        HSSFUtils.fileDownloadActivityList(response, activityList);
    }

    @RequestMapping("/workbench/activity/exportActivitysByIds.do")
    public void exportActivitysByIds(String[] id, HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.queryActivitysByIds(id);
        HSSFUtils.fileDownloadActivityList(response, activityList);
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile, HttpSession session) {
        User loginUser = (User) session.getAttribute(Contants.SESSION_USER);
        String loginUserId = loginUser.getId();
        ReturnObject returnObject = new ReturnObject();
        // 把excel写入磁盘目录
        InputStream is = null;
        try {
            is = activityFile.getInputStream();
            // 解析excel文件
            HSSFWorkbook wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(loginUserId);
                activity.setCreateTime(DateUtils.formateDateTime(new Date()));
                activity.setCreateBy(loginUserId);
                activity.setEditTime("");
                activity.setEditBy("");
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);

                    String cellValue = HSSFUtils.getCellValueForStr(cell);
                    if (j == 0) {
                        activity.setName(cellValue);
                    } else if (j == 1) {
                        activity.setStartDate(cellValue);
                    } else if (j == 2) {
                        activity.setEndDate(cellValue);
                    } else if (j == 3) {
                        activity.setCost(cellValue);
                    } else if (j == 4) {
                        activity.setDescription(cellValue);
                    }
                }
                activityList.add(activity);
            }
            int count = activityService.saveCreateActivityByList(activityList);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCESS);
            returnObject.setData(count);
        } catch (IOException e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙, 请稍后重试");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id, HttpServletRequest request) {
        Activity activity = activityService.queryActivityDetailById(id);
        List<ActivityRemark> activitieRemarkList = activityRemarkService.queryActivityRemarkListByActivityId(id);
        request.setAttribute("activity", activity);
        request.setAttribute("activitieRemarkList", activitieRemarkList);
        return "workbench/activity/detail";
    }
}