package com.jch.crm.workbench.service;

import com.jch.crm.workbench.domain.ActivityRemark;
import com.jch.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkService {

    List<ClueRemark> queryClueRemarkListByClueId(String clueId);
}
