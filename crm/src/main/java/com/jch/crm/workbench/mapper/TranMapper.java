package com.jch.crm.workbench.mapper;

import com.jch.crm.workbench.domain.Tran;

public interface TranMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Wed Sep 27 18:23:03 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Wed Sep 27 18:23:03 CST 2023
     */
    int insert(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Wed Sep 27 18:23:03 CST 2023
     */
    int insertSelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Wed Sep 27 18:23:03 CST 2023
     */
    Tran selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Wed Sep 27 18:23:03 CST 2023
     */
    int updateByPrimaryKeySelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Wed Sep 27 18:23:03 CST 2023
     */
    int updateByPrimaryKey(Tran record);

    /**
     * 根据id查询交易的明细信息
     * @param id
     * @return
     */
    Tran selectTranForDetailById(String id);
}