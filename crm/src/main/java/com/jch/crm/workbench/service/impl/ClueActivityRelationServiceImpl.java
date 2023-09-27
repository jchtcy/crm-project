package com.jch.crm.workbench.service.impl;

import com.jch.crm.workbench.domain.ClueActivityRelation;
import com.jch.crm.workbench.mapper.ClueActivityRelationMapper;
import com.jch.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int saveClueActivityRelationBatch(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.inserClueActivityRelationBatch(list);
    }

    @Override
    public int deleterClueActivityRelationByCondition(ClueActivityRelation clueActivityRelation) {
        return clueActivityRelationMapper.deleterClueActivityRelationByCondition(clueActivityRelation);
    }
}
