package com.jch.crm.workbench.service;

import com.jch.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {

    int saveClueActivityRelationBatch(List<ClueActivityRelation> list);

    int deleterClueActivityRelationByCondition(ClueActivityRelation clueActivityRelation);
}
