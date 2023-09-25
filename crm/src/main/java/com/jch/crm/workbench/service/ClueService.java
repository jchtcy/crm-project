package com.jch.crm.workbench.service;

import com.jch.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueService {

    public List<Clue> queryClueListByCondition(Clue clue);

    public int saveCreateClue(Clue clue);

    public Clue queryClueById(String id);

    public int saveEditClue(Clue clue);

    public int deleteClueByIds(String[] ids);
}
