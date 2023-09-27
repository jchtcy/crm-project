package com.jch.crm.workbench.service;

import com.jch.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {

    public List<Clue> queryClueListByCondition(Clue clue);

    public int saveCreateClue(Clue clue);

    public Clue queryClueById(String id);

    public int saveEditClue(Clue clue);

    public int deleteClueByIds(String[] ids);

    public Clue queryClueDetailById(String id);

    void saveConvert(Map<String, Object> map);
}
