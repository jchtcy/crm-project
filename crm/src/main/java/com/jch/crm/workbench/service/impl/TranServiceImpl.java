package com.jch.crm.workbench.service.impl;

import com.jch.crm.settings.commons.contants.Contants;
import com.jch.crm.settings.commons.utils.DateUtils;
import com.jch.crm.settings.commons.utils.UUIDUtils;
import com.jch.crm.settings.domain.User;
import com.jch.crm.workbench.domain.Customer;
import com.jch.crm.workbench.domain.Tran;
import com.jch.crm.workbench.domain.TranHistory;
import com.jch.crm.workbench.mapper.CustomerMapper;
import com.jch.crm.workbench.mapper.TranHistoryMapper;
import com.jch.crm.workbench.mapper.TranMapper;
import com.jch.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("tranService")
public class TranServiceImpl implements TranService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public void saveCreateTran(Map<String, Object> map) {
        String customerName=(String) map.get("customerName");
        User user=(User) map.get(Contants.SESSION_USER);
        //根据name精确查询客户
        Customer customer=customerMapper.selectCustomerByName(customerName);
        //如果客户不存在，则新建客户
        if(customer==null){
            customer=new Customer();
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setId(UUIDUtils.getUUID());
            customer.setCreateTime(DateUtils.formateDateTime(new Date()));
            customer.setCreateBy(user.getId());
            customerMapper.insert(customer);
        }
        //保存创建的交易
        Tran tran=new Tran();
        tran.setStage((String) map.get("stage"));
        tran.setOwner((String) map.get("owner"));
        tran.setNextContactTime((String) map.get("nextContactTime"));
        tran.setName((String) map.get("name"));
        tran.setMoney((String) map.get("money"));
        tran.setId(UUIDUtils.getUUID());
        tran.setExpectedDate((String) map.get("expectedDate"));
        tran.setCustomerId(customer.getId());
        tran.setCreateTime(DateUtils.formateDateTime(new Date()));
        tran.setCreateBy(user.getId());
        tran.setContactSummary((String) map.get("contactSummary"));
        tran.setContactsId((String) map.get("contactsId"));
        tran.setActivityId((String) map.get("activityId"));
        tran.setDescription((String) map.get("description"));
        tran.setSource((String) map.get("source"));
        tran.setType((String) map.get("type"));
        tranMapper.insert(tran);
        //保存交易历史
        TranHistory tranHistory=new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtils.formateDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }
}
