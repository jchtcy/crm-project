package com.jch.crm.workbench.service;

import com.jch.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    int saveCreateActivity(Activity activity);

    List<Activity> listActivityByConditionForPage(Map<String, Object> map);

    int queryCountOfActivityByCondition(Map<String, Object> map);

    int deleteActivityByIds(String[] ids);
}
