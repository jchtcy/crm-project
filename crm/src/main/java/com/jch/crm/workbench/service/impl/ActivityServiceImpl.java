package com.jch.crm.workbench.service.impl;

import com.jch.crm.workbench.domain.Activity;
import com.jch.crm.workbench.mapper.ActivityMapper;
import com.jch.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> listActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int updateEditActivity(Activity activity) {
        return activityMapper.updateEditActivity(activity);
    }

    @Override
    public List<Activity> queryAllActivitys() {
        return activityMapper.selectAllActivitys();
    }

    @Override
    public List<Activity> queryActivitysByIds(String[] ids) {
        return activityMapper.selectActivityByIds(ids);
    }

    @Override
    public int saveCreateActivityByList(List<Activity> activityList) {
        return activityMapper.insetActivityByList(activityList);
    }

    @Override
    public Activity queryActivityDetailById(String id) {
        return activityMapper.selectActivityDetailById(id);
    }

    @Override
    public List<Activity> queryActivityDetailByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityDetailByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivityDetailByIds(String[] ids) {
        return activityMapper.selectActivityDetailByIds(ids);
    }

    @Override
    public List<Activity> queryActivityDetailByClueId(String clueId) {
        return activityMapper.selectActivityDetailByClueId(clueId);
    }

    @Override
    public List<Activity> queryActivityConvertByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityConvertByNameClueId(map);
    }
}
