package com.jch.crm.workbench.service.impl;

import com.jch.crm.workbench.domain.ActivityRemark;
import com.jch.crm.workbench.mapper.ActivityRemarkMapper;
import com.jch.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    private ActivityRemarkMapper activitieRemarkMapper;

    @Override
    public List<ActivityRemark> queryActivityRemarkListByActivityId(String activityId) {
        return activitieRemarkMapper.selectActivityRemarkListByactivityId(activityId);
    }

    @Override
    public int saveActivityRemarkForSelective(ActivityRemark activitieRemark) {
        return activitieRemarkMapper.insertSelective(activitieRemark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activitieRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateActivityRemarkById(ActivityRemark activityRemark) {
        return activitieRemarkMapper.updateByPrimaryKeySelective(activityRemark);
    }
}
