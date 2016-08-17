package com.yanglin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanglin.dao.HistorycaseInfoMapper;
import com.yanglin.dao.LossaccessmentCasualitesMapper;
import com.yanglin.dao.LossaccessmentLaborsMapper;
import com.yanglin.dao.LossaccessmentMapper;
import com.yanglin.dao.LossaccessmentOpinionsMapper;
import com.yanglin.dao.LossaccessmentSparepartsMapper;
import com.yanglin.dao.LossaccessmentVehicleMapper;
import com.yanglin.dao.PolicyCategoryMapper;
import com.yanglin.dao.PolicyRiskclassMapper;
import com.yanglin.dao.ReportinfoMapper;
import com.yanglin.dao.SurveyInfoMapper;
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
import com.yanglin.service.UserServiceI;


@Service("userService")
public class UserServiceImpl implements UserServiceI{
	
//	@Autowired
//	private UserMapper userMapper;
	//定损信息
	@Autowired
	private LossaccessmentMapper lossaccessmentMapper;
	
	@Autowired
	private LossaccessmentLaborsMapper lossaccessmentLaborsMapper;
	
	@Autowired
	private LossaccessmentSparepartsMapper lossaccessmentSparepartsMapper;
	
	@Autowired
	private LossaccessmentVehicleMapper lossaccessmentVehicleMapper;
	
	@Autowired
	private LossaccessmentCasualitesMapper lossaccessmentCasualitesMapper;
	
	@Autowired
	private ReportinfoMapper reportinfoMapper;
	
	@Autowired
	private SurveyInfoMapper surveyInfoMapper;
	
	@Autowired
	private LossaccessmentOpinionsMapper lossaccessmentOpinionsMapper;
	
	@Autowired
	private PolicyCategoryMapper policyCategoryMapper;
	
	@Autowired
	private PolicyRiskclassMapper policyRiskclassMapper;
	
	@Autowired
	private HistorycaseInfoMapper historycaseInfoMapper;

	
    public List<String> getDistinctReportNo() {
        return reportinfoMapper.getDistinctReportNo();
    }

//    public List<ClaimReportInfo> getReportInfo(String reportNo) {
//        return userMapper.getReportInfo(reportNo);
//    }
    
    public List<Lossaccessment> getLossInfo(String claimNumber){
        return lossaccessmentMapper.getLossInfoByClaimNumber(claimNumber);
    }

    public Lossaccessment getLossInfoByClaimAndVehicleNumber(String claimNumer, String chepaihao) {
        return lossaccessmentMapper.getLossInfoByClaimAndVehicleNumber(claimNumer,chepaihao);
    }

    public List<LossaccessmentLabors> getLossLaborByClaimAndVehicleNumber(String reportNo, String chepaihao) {
        return lossaccessmentLaborsMapper.getLossLaborByClaimAndVehicleNumber(reportNo,chepaihao);
    }

    public List<LossaccessmentSpareparts> getSparepartsByClaimAndVehicleNumber(String reportNo, String chepaihao) {
        return lossaccessmentSparepartsMapper.getSparepartsByClaimAndVehicleNumber(reportNo,chepaihao);
    }

    public LossaccessmentVehicle getVehicleInfoByClaimAndBrandNumber(String reportNo, String chepaihao) {
        return lossaccessmentVehicleMapper.getVehicleInfoByClaimAndBrandNumber(reportNo,chepaihao);
        
    }

    public LossaccessmentCasualites getLossaccessmentCasualitesByClaimnumber(String reportNo) {
        return lossaccessmentCasualitesMapper.getLossaccessmentCasualitesByClaimnumber(reportNo);
    }

    public List<Reportinfo> getReportInfoByClaimNumber(String reportNo) {
        return reportinfoMapper.getReportInfoByClaimNumber(reportNo);
    }

    public SurveyInfo getSurveyInfoByClaimAndVehicleNumber(String reportNo, String chepaihao) {
        return surveyInfoMapper.getSurveyInfoByClaimAndVehicleNumber(reportNo,chepaihao);
    }

    public List<LossaccessmentOpinions> getLossOptionsByClaimAndVehicleNumber(String reportNo, String chepaihao) {
        return lossaccessmentOpinionsMapper.getLossOptionsByClaimAndVehicleNumber(reportNo,chepaihao);
    }

    public List<String> getBaoDanListByClaimNumber(String reportNo) {
        return reportinfoMapper.getBaoDanListByClaimNumber(reportNo);
    }

    public PolicyCategory getBanDanInfoByBaodanhao(String baoDanHao) {
        return policyCategoryMapper.getBanDanInfoByBaodanhao(baoDanHao);
    }

    public List<PolicyRiskclass> getBaoxianXianbieInfo(String baoDanHao) {
        return policyRiskclassMapper.getBaoxianXianbieInfo(baoDanHao);
    }

    public List<HistorycaseInfo> getLishiPeianLiebiao(String baoDanHao) {
        return historycaseInfoMapper.getLishiPeianLiebiao(baoDanHao);
    }
    
    
    
    

}
