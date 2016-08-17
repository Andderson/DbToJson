package com.yanglin.dao;

import java.util.List;

import com.insuranceservices.model.claim.ClaimReportInfo;
/**
 * 
 * 
 * 
 * 
 * @author Thomas
 *
 */
public interface UserMapper {

//	int deleteByPrimaryKey(String userId);
//
//	int insert(User record);
//
//	int insertSelective(User record);
//
//	User selectByPrimaryKey(String userId);
//
//	int updateByPrimaryKeySelective(User record);
//
//	int updateByPrimaryKey(User record);

	
    List<String> getDistinctReportNo();

    List<ClaimReportInfo> getReportInfo(String reportNo);


}
