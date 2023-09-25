package com.jch.crm.workbench.service.impl;

import com.jch.crm.workbench.domain.ActivityRemark;
import com.jch.crm.workbench.domain.ClueRemark;
import com.jch.crm.workbench.mapper.ClueRemarkMapper;
import com.jch.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Override
    public List<ClueRemark> queryClueRemarkListByClueId(String clueId) {
        return clueRemarkMapper.queryClueRemarkListByClueId(clueId);
    }
}
