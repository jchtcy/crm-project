package com.jch.crm.workbench.service.impl;

import com.jch.crm.settings.commons.contants.Contants;
import com.jch.crm.settings.commons.utils.DateUtils;
import com.jch.crm.settings.commons.utils.UUIDUtils;
import com.jch.crm.settings.domain.User;
import com.jch.crm.workbench.domain.*;
import com.jch.crm.workbench.mapper.*;
import com.jch.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

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

    @Override
    public void saveConvert(Map<String, Object> map) {
        String clueId = (String) map.get("clueId");
        User user = (User) map.get(Contants.SESSION_USER);
        String isCreateTran = (String) map.get("isCreateTran");
        // 查询线索
        Clue clue = clueMapper.selectByPrimaryKey(clueId);
        // 存入客户表
        Customer customer = new Customer();
        customer.setId(UUIDUtils.getUUID());
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formateDateTime(new Date()));
        customer.setDescription(clue.getDescription());
        customer.setName(clue.getCompany());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setOwner(user.getId());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
        customerMapper.insert(customer);
        // 存入联系人表
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formateDateTime(new Date()));
        contacts.setCustomerId(customer.getId());
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contactsMapper.insert(contacts);
        // 查询线索备注
        List<ClueRemark> clueRemarkList = clueRemarkMapper.queryClueRemarkListByClueId(clueId);
        // 存入客户备注表和联系人备注表
        if (clueRemarkList != null && clueRemarkList.size() > 0) {
            List<CustomerRemark> customerRemarkList = new ArrayList<>();
            List<ContactsRemark> contactsRemarkList = new ArrayList<>();
            CustomerRemark customerRemark = null;
            ContactsRemark contactsRemark = null;
            for (ClueRemark clueRemark : clueRemarkList) {
                // 客户备注表数据
                customerRemark = new CustomerRemark();
                customerRemark.setId(UUIDUtils.getUUID());
                customerRemark.setCreateBy(clueRemark.getCreateBy());
                customerRemark.setCreateTime(clueRemark.getCreateTime());
                customerRemark.setCustomerId(customer.getId());
                customerRemark.setEditBy(clueRemark.getEditBy());
                customerRemark.setEditFlag(clueRemark.getEditFlag());
                customerRemark.setEditTime(clueRemark.getEditTime());
                customerRemark.setNoteContent(clueRemark.getNoteContent());
                customerRemarkList.add(customerRemark);

                // 联系人备注表数据
                contactsRemark = new ContactsRemark();
                contactsRemark.setContactsId(contacts.getId());
                contactsRemark.setCreateBy(clueRemark.getCreateBy());
                contactsRemark.setCreateTime(clueRemark.getCreateTime());
                contactsRemark.setEditBy(clueRemark.getEditBy());
                contactsRemark.setEditFlag(clueRemark.getEditFlag());
                contactsRemark.setEditTime(clueRemark.getEditTime());
                contactsRemark.setId(UUIDUtils.getUUID());
                contactsRemark.setNoteContent(clueRemark.getNoteContent());
                contactsRemarkList.add(contactsRemark);
            }
            // 存入客户备注表
            customerRemarkMapper.insertCustomerRemarkByList(customerRemarkList);
            // 存入联系人备注表
            contactsRemarkMapper.insertContactsRemarkByList(contactsRemarkList);
        }
        // 根据clueId查询线索和市场活动的关联关系
        List<ClueActivityRelation> clueActivityRelationList =
                clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);

        // 存入联系人和市场活动关联表
        if (clueActivityRelationList != null && clueActivityRelationList.size() > 0) {
            List<ContactsActivityRelation> contactsActivityRelationList = new ArrayList<>();
            ContactsActivityRelation contactsActivityRelation = null;
            for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
                contactsActivityRelation = new ContactsActivityRelation();
                contactsActivityRelation.setId(UUIDUtils.getUUID());
                contactsActivityRelation.setContactsId(contacts.getId());
                contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
                contactsActivityRelationList.add(contactsActivityRelation);
            }
            contactsActivityRelationMapper.insertContactsActivityRelationByList(contactsActivityRelationList);
        }
        // 存入交易表
        if ("true".equals(isCreateTran)) {// 如果创建了交易表
            Tran tran = new Tran();
            tran.setId(UUIDUtils.getUUID());
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formateDateTime(new Date()));
            tran.setCustomerId(customer.getId());
            tran.setExpectedDate((String) map.get("expectedDate"));
            tran.setMoney((String) map.get("money"));
            tran.setName((String) map.get("name"));
            tran.setOwner(user.getId());
            tran.setStage((String) map.get("stage"));

            tranMapper.insert(tran);

            // 把该线索下的备注转到交易备注下
            if (clueRemarkList != null && clueRemarkList.size() > 0) {
                List<TranRemark> tranRemarkList = new ArrayList<>();
                TranRemark tranRemark = null;
                for (ClueRemark clueRemark : clueRemarkList) {
                    tranRemark = new TranRemark();
                    tranRemark.setId(UUIDUtils.getUUID());
                    tranRemark.setCreateBy(clueRemark.getCreateBy());
                    tranRemark.setCreateTime(clueRemark.getCreateTime());
                    tranRemark.setEditBy(clueRemark.getEditBy());
                    tranRemark.setEditTime(clueRemark.getEditTime());
                    tranRemark.setEditFlag(clueRemark.getEditFlag());
                    tranRemark.setNoteContent(clueRemark.getNoteContent());
                    tranRemark.setTranId(tran.getId());
                    tranRemarkList.add(tranRemark);
                }
                tranRemarkMapper.insertTranRemarkByList(tranRemarkList);
            }
        }
        // 删除线索下所有备注
        clueRemarkMapper.deleteClueRemarkByClueIdInt(clueId);
        // 删除线索和市场活动的关联关系
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);
        // 删除该线索
        clueMapper.deleteByPrimaryKey(clueId);
    }
}
