package com.jch.crm.workbench.service;

import com.jch.crm.workbench.domain.Tran;

import java.util.Map;

public interface TranService {
    void saveCreateTran(Map<String,Object> map);

    Tran queryTranForDetailById(String id);
}
