package com.jch.crm.workbench.service;

import com.jch.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    int saveCreateActivity(Activity activity);

    List<Activity> listActivityByConditionForPage(Map<String, Object> map);

    int queryCountOfActivityByCondition(Map<String, Object> map);

    int deleteActivityByIds(String[] ids);

    Activity queryActivityById(String id);

    int updateEditActivity(Activity activity);

    List<Activity> queryAllActivitys();

    List<Activity> queryActivitysByIds(String[] ids);

    int saveCreateActivityByList(List<Activity> activityList);

    Activity queryActivityDetailById(String id);

    List<Activity> queryActivityDetailByNameClueId(Map<String, Object> map);

    List<Activity> queryActivityDetailByIds(String[] ids);

    List<Activity> queryActivityDetailByClueId(String clueId);

    List<Activity> queryActivityConvertByNameClueId(Map<String, Object> map);
}
