package com.jch.crm.settings.service;

import com.jch.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueService {

    public List<DicValue> queryDicValueByTypeCode(String typeCode);
}
