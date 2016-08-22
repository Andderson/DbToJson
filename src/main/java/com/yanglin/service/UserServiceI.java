package com.yanglin.service;

import java.util.List;

import com.yanglin.model.HistorycaseInfo;
import com.yanglin.model.Lossaccessment;
import com.yanglin.model.LossaccessmentCasualites;
import com.yanglin.model.LossaccessmentLabors;
import com.yanglin.model.LossaccessmentOpinions;
import com.yanglin.model.LossaccessmentSpareparts;
import com.yanglin.model.LossaccessmentVehicle;
import com.yanglin.model.PolicyCategory;
import com.yanglin.model.PolicyRiskclass;
import com.yanglin.model.Reportinfo;
import com.yanglin.model.SurveyInfo;

public interface UserServiceI {
	/**
	 * 添加用户
	 * @param user
	 */
//	void addUser(User user);

	/**
	 * 根据用户id获取用户
	 * @param userId
	 * @return
	 */
//	User getUserById(String userId);

	
    List<String> getDistinctReportNo();
    
//    List<ClaimReportInfo> getReportInfo(String reportNo);
    
    List<Lossaccessment> getLossInfo(String claimNumber);
    
    /**
     * 根据报案号和车牌号获取定损信息
     * @return
     */
    Lossaccessment getLossInfoByClaimAndVehicleNumber(String claimNumer,String chepaihao);

    /**
     * 根据报案号和车牌号获取定损-工时信息
     * @param reportNo
     * @param chepaihao
     */
    List<LossaccessmentLabors> getLossLaborByClaimAndVehicleNumber(String reportNo, String chepaihao);

    List<LossaccessmentSpareparts> getSparepartsByClaimAndVehicleNumber(String reportNo, String chepaihao);

    
    LossaccessmentVehicle getVehicleInfoByClaimAndBrandNumber(String reportNo, String chepaihao);

    LossaccessmentCasualites getLossaccessmentCasualitesByClaimnumber(String reportNo);

    List<Reportinfo> getReportInfoByClaimNumber(String reportNo);

    SurveyInfo getSurveyInfoByClaimAndVehicleNumber(String reportNo, String chepaihao);

    List<LossaccessmentOpinions> getLossOptionsByClaimAndVehicleNumber(String reportNo, String chepaihao);

    List<String> getBaoDanListByClaimNumber(String reportNo);

    
    PolicyCategory getBanDanInfoByBaodanhao(String baoDanHao);

    List<PolicyRiskclass> getBaoxianXianbieInfo(String baoDanHao);

    List<HistorycaseInfo> getLishiPeianLiebiao(String baoDanHao);
 }
