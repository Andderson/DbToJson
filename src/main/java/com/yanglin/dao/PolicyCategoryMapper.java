package com.yanglin.dao;

import com.yanglin.model.PolicyCategory;

public interface PolicyCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_category
     *
     * @mbggenerated
     */
    int insert(PolicyCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table policy_category
     *
     * @mbggenerated
     */
    int insertSelective(PolicyCategory record);

    
    PolicyCategory getBanDanInfoByBaodanhao(String baoDanHao);
}