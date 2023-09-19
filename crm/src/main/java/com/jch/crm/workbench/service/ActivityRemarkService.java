package com.jch.crm.workbench.service;

import com.jch.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    List<ActivityRemark> queryActivityRemarkListByActivityId(String activityId);

    int saveActivityRemarkForSelective(ActivityRemark activitieRemark);

    int deleteActivityRemarkById(String id);

    int updateActivityRemarkById(ActivityRemark activityRemark);
}
