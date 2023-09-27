package com.jch.crm.workbench.service.impl;

import com.jch.crm.workbench.domain.Clue;
import com.jch.crm.workbench.mapper.ClueMapper;
import com.jch.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Override
    public List<Clue> queryClueListByCondition(Clue clue) {
        return clueMapper.selectClueListByCondition(clue);
    }

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insert(clue);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveEditClue(Clue clue) {
        return clueMapper.updateByPrimaryKeySelective(clue);
    }

    @Override
    public int deleteClueByIds(String[] ids) {
        return clueMapper.deleteClueByIds(ids);
    }

    @Override
    public Clue queryClueDetailById(String id) {
        return clueMapper.queryClueDetailById(id);
    }
}
