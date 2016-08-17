package com.yanglin.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insuranceservices.model.base.BasicVehicleInfo;
import com.insuranceservices.model.base.ClaimPositionPriceInfo;
import com.insuranceservices.model.claim.ClaimModel;
import com.insuranceservices.model.claim.ClaimReportInfo;
import com.insuranceservices.model.claim.InjuredPerson;
import com.insuranceservices.model.claim.InsuranceTypeInfo;
import com.insuranceservices.model.claim.InvestigationInfo;
import com.insuranceservices.model.claim.LossAssessmentInfo;
import com.insuranceservices.model.claim.RepairFactoryInfo;
import com.insuranceservices.model.claim.VehicleOwnerInfo;
import com.insuranceservices.model.claim.insurancepolicy.InsurancePolicy;
import com.insuranceservices.model.claim.insurancepolicy.PolicyChangeLog;
import com.insuranceservices.model.claim.insurancepolicy.PolicyHistoryClaimSettlement;
import com.insuranceservices.model.claim.insurancepolicy.PolicyRiskCoverage;
import com.insuranceservices.model.claim.insurancepolicy.PolicyVehicleInfo;
import com.insuranceservices.model.claim.investigation.InvestigationTaskInfo;
import com.insuranceservices.model.claim.investigation.InvestigationVehicleInfo;
import com.insuranceservices.model.claim.lossassessment.LaborModel;
import com.insuranceservices.model.claim.lossassessment.LossAssessmentComment;
import com.insuranceservices.model.claim.lossassessment.SparePartModel;
import com.insuranceservices.model.claim.lossassessment.SparePartPriceInfo;
import com.insuranceservices.model.claim.lossassessment.VehicleInfo;
import com.insuranceservices.model.enums.Gender;
import com.insuranceservices.model.enums.InjuryType;
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

public class MyBatisTest {
	
	private UserServiceI userServiceI;
	ApplicationContext ac = null;
	private static ObjectMapper mapper;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    private static ObjectMapper getJsonMapper() {
        if (mapper==null) {
            mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            mapper.enable(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS);
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }
        
        return mapper;
    }	
	
	@Before
	public void before(){
		 //使用"spring-context.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
		 ac = new ClassPathXmlApplicationContext(new String[]{"spring-context.xml","spring-mybatis.xml"});
		 //从Spring容器中根据bean的id取出我们要使用的userService对象
		 userServiceI = (UserServiceI) ac.getBean("userService");
	}
	
//	@Test
//	public void testAddUser(){
//	    User user = new User();
//	    user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
//	    user.setUserName("白虎神皇xdp");
//	    user.setUserBirthday(new Date());
//	    user.setUserSalary(10000D);
////	    userServiceI.addUser(user);
//	}
	
	/**
	 * FileWritter写入文件
	 */
	@Test
    public void exportUser() {
	    
	    List<String> distinctReportNo = userServiceI.getDistinctReportNo();
	    
	    System.out.println("报案号个数： " + distinctReportNo.size());
	    
        try {
            for (String reportNo : distinctReportNo) {
                
                //定损信息
                List<Lossaccessment> lossInfo = userServiceI.getLossInfo(reportNo);
                if (lossInfo != null && lossInfo.size() > 0) {
                    for (Lossaccessment lossaccessment : lossInfo) {

                        ClaimModel claimModel = new ClaimModel();
                        System.out.println(reportNo);
                        //报案号
                        claimModel.setClaimNumber(reportNo);
                        //公司ID
//                        claimModel.setInsuranceCompanyId(6);
                        
                        //定损信息列表
                        List<LossAssessmentInfo> lossAssessmentInfos = new ArrayList<LossAssessmentInfo>();
                        //单个定损信息
                        LossAssessmentInfo lossAssessmentInfo = new LossAssessmentInfo();
                        
                        //车牌号
                        String chepaihao = lossaccessment.getChepaihao();
                        
                        //定损信息  开始
                        //定损信息-修理厂信息
                        RepairFactoryInfo repairFactoryInfo = new RepairFactoryInfo();
                        if (lossaccessment.getXiulichangMingcheng() != null) {
                            repairFactoryInfo.setName(lossaccessment.getXiulichangMingcheng());  
                        }
                        if (lossaccessment.getXiulichangZizhi() != null) {
                            repairFactoryInfo.setQualificationName(lossaccessment.getXiulichangZizhi()); 
                        }
                        if (lossaccessment.getXiulichangLeixing() != null) {
                            repairFactoryInfo.setTypeName(lossaccessment.getXiulichangLeixing());
                        }
                        
                        lossAssessmentInfo.setRepairFactoryInfo(repairFactoryInfo);
                        
                        if (lossaccessment.getDingsunrenMingcheng() != null) {
                            lossAssessmentInfo.setLossAssessorName(lossaccessment.getDingsunrenMingcheng());
                        }
                        if (lossaccessment.getDingsunrenDaima() != null) {
                            lossAssessmentInfo.setLossAssessorCode(lossaccessment.getDingsunrenDaima());
                        }
                        if (lossaccessment.getDingsunjigouMingcheng() != null) {
                            lossAssessmentInfo.setLossAssessmentComName(lossaccessment.getDingsunjigouMingcheng()); 
                        }
                        if (lossaccessment.getDingsunjigouDaima() != null) {
                            lossAssessmentInfo.setLossAssessmentComCode(lossaccessment.getDingsunjigouDaima());
                        }
                        if (lossaccessment.getDingsunLeixing() != null) {
                            lossAssessmentInfo.setLossAssessmentType(lossaccessment.getDingsunLeixing());
                        }
                        if (lossaccessment.getDingsunShijian() != null) {
                            lossAssessmentInfo.setLossAssessmentDate(lossaccessment.getDingsunShijian());
                        }
                            
                        lossAssessmentInfo.setLossAssessmentTotalLossAmount(lossaccessment.getDingsunHejijine().doubleValue());
                        
                        lossAssessmentInfo.setLossAssessmentTotalRemainedValue(lossaccessment.getDingsunZongcanzhijine().doubleValue());
                        
                        lossAssessmentInfo.setRescueFee(lossaccessment.getShijiufei().doubleValue());
                        
                        if (lossaccessment.getBaoxianleixingXianbie() != null) {
                            InsuranceTypeInfo insuranceTypeInfo = new InsuranceTypeInfo();
                            insuranceTypeInfo.setInsuranceTypeName(lossaccessment.getBaoxianleixingXianbie());
                            
                            lossAssessmentInfo.setInsuranceTypeInfo(insuranceTypeInfo);
                        }
                        
                        //定损中的车辆信息
                        VehicleInfo vehicleInfo = new VehicleInfo();
                        if (lossaccessment.getQibaoShijian() != null) {
                            vehicleInfo.setInsuranceBeginTime(lossaccessment.getQibaoShijian());
                        }
                        if (lossaccessment.getZhongbaoShijian() != null) {
                            
                            vehicleInfo.setInsuranceEndTime(lossaccessment.getZhongbaoShijian());
                        }
                        
                        //定损车辆信息  开始
                        LossaccessmentVehicle lossaccessmentVehicle = userServiceI.getVehicleInfoByClaimAndBrandNumber(reportNo,chepaihao);
                        if (lossaccessmentVehicle != null) {
                            if (lossaccessmentVehicle.getShifouBiaodiche() != null) {
                                if ("是".equals(lossaccessmentVehicle.getShifouBiaodiche())) {
                                    vehicleInfo.setMainVehicle(true);
                                }else if ("否".equals(lossaccessmentVehicle.getShifouBiaodiche())) {
                                    vehicleInfo.setMainVehicle(false);
                                }
                            }
                            if (lossaccessmentVehicle.getChepaihao() != null) {
                                vehicleInfo.setRegistrationNumber(lossaccessmentVehicle.getChepaihao());
                            }
                            if (lossaccessmentVehicle.getVin() != null) {
                                vehicleInfo.setVIN(lossaccessmentVehicle.getVin());
                            }
                            if (lossaccessmentVehicle.getDingsunChexing() != null) {
                                vehicleInfo.setLossAssessmentModel(lossaccessmentVehicle.getDingsunChexing());
                            }
                            if (lossaccessmentVehicle.getZuoweishu() != null) {
                                vehicleInfo.setNumberOfSeats(lossaccessmentVehicle.getZuoweishu());
                            }
                            if (lossaccessmentVehicle.getCheliangZhonglei() != null) {
//                                if ("客车".equals(lossaccessmentVehicle.getCheliangZhonglei())) {
//                                    vehicleInfo.
//                                }else if ("拖拉机".equals(lossaccessmentVehicle.getCheliangZhonglei())) {
//                                    
//                                }else if ("两轮摩托车".equals(lossaccessmentVehicle.getCheliangZhonglei())) {
//                                    
//                                }
                                
                                vehicleInfo.setVehicleTypeName(lossaccessmentVehicle.getCheliangZhonglei());
                            }
                            if (lossaccessmentVehicle.getShiyongxingzhi() != null) {
                                vehicleInfo.setUsageName(lossaccessmentVehicle.getShiyongxingzhi());
                            }
                            if (lossaccessmentVehicle.getRanliaoLeixing() != null) {
                                vehicleInfo.setFuelType(lossaccessmentVehicle.getRanliaoLeixing());
                            }
                            //排量
                            if (lossaccessmentVehicle.getPeiliang() != null) {
                                
                                vehicleInfo.setEngineDisplacement(lossaccessmentVehicle.getPeiliang().doubleValue());
                            }
                            
                            //变速箱类型
//                            if (lossaccessmentVehicle) {
//                                
//                            }
                            if (lossaccessmentVehicle.getXingshiLichengshu() != null) {
                                
                                vehicleInfo.setMileage(lossaccessmentVehicle.getXingshiLichengshu().doubleValue());
                            }
                            if (lossaccessmentVehicle.getShengchanNianfen() != null) {
                                
                                vehicleInfo.setProductionDate(lossaccessmentVehicle.getShengchanNianfen());
                            }
                            if (lossaccessmentVehicle.getShiyongNianxian() != null) {
                                
                                vehicleInfo.setVehicleUsageAge(lossaccessmentVehicle.getShiyongNianxian());
                            }
                             
                        }
                        //定损车辆信息  结束
                        
                        lossAssessmentInfo.setVehicle(vehicleInfo);
                        
                        
                        //获取工时信息   开始
                        List<LossaccessmentLabors> lossaccessmentLabors = userServiceI.getLossLaborByClaimAndVehicleNumber(reportNo,chepaihao);
                        if (lossaccessmentLabors != null && lossaccessmentLabors.size() > 0) {
                            //工时合集
                            List<LaborModel> laborModels = new ArrayList<LaborModel>();
                            
                            for (int i = 0; i < lossaccessmentLabors.size(); i++) {
                                //单个工时
                                LaborModel laborModel = new LaborModel();
                                if (lossaccessmentLabors.get(i).getGongshiMingcheng() != null) {
                                    
                                    laborModel.setName(lossaccessmentLabors.get(i).getGongshiMingcheng());
                                }
                                if (lossaccessmentLabors.get(i).getGognshiLeixing() != null) {
                                    
                                    laborModel.setRepairTypeName(lossaccessmentLabors.get(i).getGognshiLeixing());
                                }
                                if (lossaccessmentLabors.get(i).getShifouShoudongshuru() != null) {
                                    
                                    if ("是".equals(lossaccessmentLabors.get(i).getShifouShoudongshuru())) {
                                        laborModel.setIsManuallyInputted(true);
                                    }else if ("否".equals(lossaccessmentLabors.get(i).getShifouShoudongshuru())) {
                                        laborModel.setIsManuallyInputted(false);
                                    }
                                    
                                }
                                if (lossaccessmentLabors.get(i).getShifouNeixiu() != null) {
                                    if ("是".equals(lossaccessmentLabors.get(i).getShifouNeixiu())) {
                                        laborModel.setIsInnerRepair(true);
                                    }else if ("否".equals(lossaccessmentLabors.get(i).getShifouNeixiu())) {
                                        laborModel.setIsInnerRepair(false);
                                    }
                                }
                                //工时--备注
                                if (lossaccessmentLabors.get(i).getBeizhu() != null && !"-".equals(lossaccessmentLabors.get(i).getBeizhu())) {
                                    laborModel.setRemark(lossaccessmentLabors.get(i).getBeizhu());
                                }
                                //工时--单价
                                ClaimPositionPriceInfo claimPositionPriceInfo = new ClaimPositionPriceInfo();
                                claimPositionPriceInfo.setUnitPrice(lossaccessmentLabors.get(i).getDanjia().floatValue());
                                claimPositionPriceInfo.setCount(1f);
                                claimPositionPriceInfo.setUnitPrice(lossaccessmentLabors.get(i).getDanjia().floatValue());
                                laborModel.setPriceInfo(claimPositionPriceInfo);
                                
                                laborModels.add(laborModel);
                            }
                            //添加工时信息
                            lossAssessmentInfo.setLabors(laborModels);
                        }
                        //获取工时信息   结束 
                        
                        //获取配件信息  开始
                        List<LossaccessmentSpareparts> lossaccessmentSpareparts = userServiceI.getSparepartsByClaimAndVehicleNumber(reportNo,chepaihao);
                        if (lossaccessmentSpareparts != null && lossaccessmentSpareparts.size() > 0) {
                            //配件列表
                            List<SparePartModel> sparePartModels = new ArrayList<SparePartModel>();
                            for (int i = 0; i < lossaccessmentSpareparts.size(); i++) {
                                //单个配件
                                SparePartModel sparePartModel = new SparePartModel();
                                
                                if (lossaccessmentSpareparts.get(i).getPeijianxiangmuMingcheng() != null) {
                                    sparePartModel.setName(lossaccessmentSpareparts.get(i).getPeijianxiangmuMingcheng());
                                }
                                if (lossaccessmentSpareparts.get(i).getShifouShoudongshuru() != null) {
                                    if ("是".equals(lossaccessmentSpareparts.get(i).getShifouShoudongshuru())) {
                                        sparePartModel.setIsManuallyInputted(true);
                                    }else if ("否".equals(lossaccessmentSpareparts.get(i).getShifouShoudongshuru())) {
                                        sparePartModel.setIsManuallyInputted(false);
                                    }
                                }
                                if (lossaccessmentSpareparts.get(i).getOem() != null) {
                                    sparePartModel.setOem(lossaccessmentSpareparts.get(i).getOem());
                                }
                                
                                if (lossaccessmentSpareparts.get(i).getShifouNeixiu() != null) {
                                    if ("是".equals(lossaccessmentSpareparts.get(i).getShifouNeixiu())) {
                                        sparePartModel.setIsInnerRepair(true);
                                    }else if ("否".equals(lossaccessmentSpareparts.get(i).getShifouNeixiu())) {
                                        sparePartModel.setIsInnerRepair(false);
                                    }
                                }
                                //配件中的：单价、数量、小计金额
                                SparePartPriceInfo sparePartPriceInfo = new SparePartPriceInfo();
//                                System.out.println("配件中的单价： " + reportNo + " " + lossaccessmentSpareparts.get(i).getDanjia().floatValue());
                                sparePartPriceInfo.setUnitPrice(lossaccessmentSpareparts.get(i).getDanjia().floatValue());
                                sparePartPriceInfo.setCount(lossaccessmentSpareparts.get(i).getShuliang().floatValue());
                                sparePartPriceInfo.setSubTotalPrice(lossaccessmentSpareparts.get(i).getXiaojiJine().doubleValue());
                                sparePartModel.setPriceInfo(sparePartPriceInfo); 
                                
                                if (lossaccessmentSpareparts.get(i).getBeizhu() != null) {
                                    sparePartModel.setRemark(lossaccessmentSpareparts.get(i).getBeizhu());
                                }
                                sparePartModels.add(sparePartModel);
                            }
                            lossAssessmentInfo.setSpareParts(sparePartModels);
                        }
                        //获取配件信息  结束
                        
                        //定损信息-定核损意见信息  开始
                        List<LossaccessmentOpinions> lossOptions = userServiceI.getLossOptionsByClaimAndVehicleNumber(reportNo,chepaihao);
                        if (lossOptions != null && lossOptions.size() > 0) {
                            List<LossAssessmentComment> lossAssessmentComments = new ArrayList<LossAssessmentComment>();
                            
                            for (LossaccessmentOpinions lossaccessmentOpinion : lossOptions) {

                                LossAssessmentComment lossAssessmentComment = new LossAssessmentComment();
                                
                                if (lossaccessmentOpinion.getHesunren() != null) {
                                    lossAssessmentComment.setCommentAuthor(lossaccessmentOpinion.getHesunren());
                                }
                                if (lossaccessmentOpinion.getJuese() != null) {
                                    
                                    lossAssessmentComment.setRole(lossaccessmentOpinion.getJuese());
                                }
                                if (lossaccessmentOpinion.getCaozuoShijian() != null) {
                                     
                                    lossAssessmentComment.setDatetime(lossaccessmentOpinion.getCaozuoShijian());
                                }
                                if (lossaccessmentOpinion.getYijianMingcheng() != null) {
                                    
                                    lossAssessmentComment.setCommentName(lossaccessmentOpinion.getYijianMingcheng());
                                }
                                if (lossaccessmentOpinion.getYijianShuoming() != null) {
                                    
                                    lossAssessmentComment.setComment(lossaccessmentOpinion.getYijianShuoming());
                                }
                                
                                lossAssessmentComments.add(lossAssessmentComment);
                            }
                            
                            lossAssessmentInfo.setComments(lossAssessmentComments);
                        }
                        //定损信息-定核损意见信息  结束
                        
                        
                        //定损信息  结束----------------------------------------------------
                        
                        //人伤信息  开始
                        LossaccessmentCasualites lossaccessmentCasualites = userServiceI.getLossaccessmentCasualitesByClaimnumber(reportNo);
                        if (lossaccessmentCasualites != null) {
                            List<InjuredPerson> injuredPersons = new ArrayList<InjuredPerson>();
                            InjuredPerson injuredPerson = new InjuredPerson();
                            
                            if (lossaccessmentCasualites.getXingming() != null) {
                                injuredPerson.setName(lossaccessmentCasualites.getXingming());
                            }
                            if (lossaccessmentCasualites.getXingbie() != null) {
                                if ("男".equals(lossaccessmentCasualites.getXingbie())) {
                                    injuredPerson.setGender(Gender.Male);
                                }else if ("女".equals(lossaccessmentCasualites.getXingbie())) {
                                    injuredPerson.setGender(Gender.Female);
                                }else {
                                    injuredPerson.setGender(Gender.Unspecified);
                                }
                            }
                            if (lossaccessmentCasualites.getShangwangLeibie() != null) {
//                                if ("伤".equals(lossaccessmentCasualites.getShangwangLeibie())) {
//                                    
//                                }else if ("".equals(lossaccessmentCasualites.getShangwangLeibie())) {
//                                    
//                                }else {
//                                    
//                                }
                                //全是伤残
                                injuredPerson.setTypeOfInjury(InjuryType.Disability);
                            }
                            
                            injuredPersons.add(injuredPerson);
                            claimModel.setInjuredPersons(injuredPersons);
                        }
                        
                        //人伤信息  结束
                        
                        
                        //报案信息  开始
                        List<Reportinfo> reportInfos = userServiceI.getReportInfoByClaimNumber(reportNo);
                        if (reportInfos != null && reportInfos.size() > 0) {
                            ClaimReportInfo claimReportInfo = new ClaimReportInfo();
                            Reportinfo reportinfo = reportInfos.get(0);
                            //报案号
                            if (StringUtils.isNoneBlank(reportinfo.getBaoanhao())) {
                                
                                claimReportInfo.setClaimNumber(reportinfo.getBaoanhao());
                            }
                            //案件来源
                            if (reportinfo.getAnjianlaiyuan() != null) {
                                
                                claimReportInfo.setClaimResource(reportinfo.getAnjianlaiyuan());
                            }
                            //历史出险次数
                            if (reportinfo.getLishiChuxiancishu() != null) {
                                
                                claimReportInfo.setHistoryAccidentCount(reportinfo.getLishiChuxiancishu());
                            }
                            //关联报案号
                            if (reportinfo.getGuanlianBaoanhao() != null) {
                                
                                claimReportInfo.setAssociatedClaimNumber(reportinfo.getGuanlianBaoanhao());
                            }
                            if (reportinfo.getPeianLeixing() != null) {
                                
                                claimReportInfo.setClaimType(reportinfo.getPeianLeixing());
                            }
                            if (reportinfo.getBaoanren() != null) {
                                claimReportInfo.setReporter(reportinfo.getBaoanren());
                            }
                            if (reportinfo.getBaoanrenDianhua() != null) {
                                
                                claimReportInfo.setReporterTel(reportinfo.getBaoanrenDianhua());
                            }
                            if (reportinfo.getChuxianshijian() != null) {
                                
                                try {
                                    claimReportInfo.setAccidentDate(sdf.parse(reportinfo.getChuxianshijian()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (reportinfo.getBaoanshijian() != null) {
                                
                                try {
                                    claimReportInfo.setReportDate(sdf.parse(reportinfo.getBaoanshijian()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (reportinfo.getShifouXianchangbaoan() != null) {
                                
                                if ("是".equals(reportinfo.getShifouXianchangbaoan())) {
                                    claimReportInfo.setReportedOnSite(true);
                                }else if ("否".equals(reportinfo.getShifouXianchangbaoan())) {
                                    claimReportInfo.setReportedOnSite(false);
                                }
                            }
                            if (reportinfo.getChuxiandidian() != null) {
                                
                                claimReportInfo.setAccidentAddress(reportinfo.getChuxiandidian());
                            }
                            if (reportinfo.getChuxianYuanyin() != null) {
                                
                                claimReportInfo.setAccidentReason(reportinfo.getChuxianYuanyin());
                            }
                            if (reportinfo.getChuxianJingguo() != null) {
                                claimReportInfo.setAccidentDescription(reportinfo.getChuxianJingguo());
                            }
                            if (reportinfo.getShifouYidichuxian() != null) {
                                
                                if ("是".equals(reportinfo.getShifouYidichuxian())) {
                                    claimReportInfo.setIsRemoteAccident(true);
                                }else if ("否".equals(reportinfo.getShifouYidichuxian())) {
                                    claimReportInfo.setIsRemoteAccident(false);
                                }
                            }
                            if (reportinfo.getBaoanJiashiyuan() != null) {
                                claimReportInfo.setDriverName(reportinfo.getBaoanJiashiyuan());
                            }
                            if (reportinfo.getBaoanJiashiyuanXingbie() != null) {
                                claimReportInfo.setDriverGender(reportinfo.getBaoanJiashiyuanXingbie());
                            }
                            
                            if (reportinfo.getShiguzeren() != null) {
                                claimReportInfo.setAccidentResponsibility(reportinfo.getShiguzeren());
                            }
                            if (reportinfo.getShiguleixing() != null) {
                                claimReportInfo.setAccidentType(reportinfo.getShiguleixing());
                            }
                            
                            if (reportinfo.getBaoanrenShifouBeibaoxianren() != null) {
                                
                                if ("是".equals(reportinfo.getBaoanrenShifouBeibaoxianren())) {
                                    claimReportInfo.setReporterIsInsured(true);
                                }else if ("否".equals(reportinfo.getBaoanrenShifouBeibaoxianren())) {
                                    claimReportInfo.setReporterIsInsured(false);
                                }
                            }
                            if (reportinfo.getBeibaoxianren() != null) {
                                
                                claimReportInfo.setTheInsuredName(reportinfo.getBeibaoxianren());
                            }
                            if (reportinfo.getBeibaoxianrenDianhua() != null) {
                                
                                claimReportInfo.setTheInsuredContact(reportinfo.getBeibaoxianrenDianhua());
                            }
                            claimModel.setReportInfo(claimReportInfo);
                        }
                        
                        //报案信息  结束
                        
                        //查勘信息  开始
                        SurveyInfo surveyInfo = userServiceI.getSurveyInfoByClaimAndVehicleNumber(reportNo,chepaihao);
                        if (surveyInfo != null) {
                            InvestigationInfo investigationInfo = new InvestigationInfo();
                            //报案号
                            if (surveyInfo.getBaoanhao() != null ) {
                                investigationInfo.setClaimNumber(surveyInfo.getBaoanhao());
                            }
                            //赔付方式
                            if (surveyInfo.getPeifufangshi() != null) {
                                
                                investigationInfo.setCompensateType(surveyInfo.getPeifufangshi());
                            }
                            //责任系数
                            if (surveyInfo.getZerenxishu() != null) {
                                
                                investigationInfo.setDutyCoefficient(surveyInfo.getZerenxishu());
                            }
                            //协商加扣比例
                            if (StringUtils.isNotBlank(surveyInfo.getXieshangjiakoubili())) {
                                
                                investigationInfo.setNegotiateDeductProportion(Double.valueOf(surveyInfo.getXieshangjiakoubili()));
                            }
                            //理赔类型
                            if (surveyInfo.getLipeileixing() != null) {
                                
                                investigationInfo.setClaimType(surveyInfo.getLipeileixing());
                            }
                            //出险原因
                            if (surveyInfo.getChuxianyuanyin() != null) {
                                investigationInfo.setAccidentReason(surveyInfo.getChuxianyuanyin());
                            }
                            //事故类型
                            if (surveyInfo.getShiguleixing() != null) {
                                
                                investigationInfo.setAccidentTypeName(surveyInfo.getShiguleixing());
                                
                            }
                            //涉及第三方车辆数
                            if (surveyInfo.getDisanfangCheliangshu() != null) {
                                
                                investigationInfo.setThirdPartyVehicleCount(Integer.valueOf(surveyInfo.getDisanfangCheliangshu()));
                            }
                            
                            //是否移动端处理
                            if (StringUtils.isNotBlank(surveyInfo.getShifouYidongduanchuli())) {
                                if ("是".equals(surveyInfo.getShifouYidongduanchuli())) {
                                    investigationInfo.setIsHandledByMobileApp(true);
                                }else if ("否".equals(surveyInfo.getShifouYidongduanchuli())) {
                                    investigationInfo.setIsHandledByMobileApp(false);
                                }                                
                            }
                            
                            //查勘信息
                            Set<InvestigationTaskInfo> investigationTaskInfoes = new HashSet<InvestigationTaskInfo>();
                            //车辆查勘信息
                            Set<InvestigationVehicleInfo> vehicleInvestigationInfoSet = new HashSet<InvestigationVehicleInfo>();
                            //单个的查勘信息
                            InvestigationTaskInfo investigationTaskInfo = new InvestigationTaskInfo();
                            //单个的车辆查勘信息
                            InvestigationVehicleInfo investigationVehicleInfo = new InvestigationVehicleInfo();
                            
                            if (surveyInfo.getChakanDaichakanren() != null) {
                                investigationTaskInfo.setInvestigator(surveyInfo.getChakanDaichakanren());
                            }
                            if (surveyInfo.getChakanJigoumingcheng() != null) {
                                investigationTaskInfo.setInvestigatorOrg(surveyInfo.getChakanJigoumingcheng());
                            }
                            if (surveyInfo.getChakanRiqi() != null) {
                                investigationTaskInfo.setInvestigationDate(surveyInfo.getChakanRiqi());
                            }
                            if (surveyInfo.getChakanDidian() != null) {
                                investigationTaskInfo.setInvestigationAddress(surveyInfo.getChakanDidian());
                            }
                            
                            if (surveyInfo.getShifouYidongduanchuli() != null) {
                                
                                if ("是".equals(surveyInfo.getShifouYidongduanchuli())) {
//                                    System.out.println("查勘：是否移动端处理： "+reportNo + " ; " + surveyInfo.getShifouYidongduanchuli());
                                    investigationTaskInfo.setIsHandledByMobileApp(true);
                                }else if ("否".equals(surveyInfo.getShifouYidongduanchuli())) {
//                                    System.out.println("查勘：是否移动端处理： "+reportNo + " ; " + surveyInfo.getShifouYidongduanchuli());
                                    investigationTaskInfo.setIsHandledByMobileApp(false);
                                }
                            }
                            if (surveyInfo.getChakanBaogao() != null) {
                                
                                investigationTaskInfo.setInvestigationComments(surveyInfo.getChakanBaogao());
                            }
                            
                            if (surveyInfo.getChepaihao() != null) {
                                investigationVehicleInfo.setRegistrationNumber(surveyInfo.getChepaihao());
                            }
                            if (surveyInfo.getVin() != null) {
                                investigationVehicleInfo.setVIN(surveyInfo.getVin());
                            }
                            if (surveyInfo.getDingsunChexing() != null) {
                                
                                investigationVehicleInfo.setLossAssessmentModel(surveyInfo.getDingsunChexing());
                            }
                            //车主信息
                            VehicleOwnerInfo vehicleOwnerInfo = new VehicleOwnerInfo();
                            if (surveyInfo.getChezhuXingming() != null) {
                                vehicleOwnerInfo.setName(surveyInfo.getChezhuXingming());
                            }
                            if (surveyInfo.getZhengjianLeixing() != null) {
                                vehicleOwnerInfo.setCredentialType(surveyInfo.getZhengjianLeixing());
                            }
                            investigationVehicleInfo.setVehicleOwner(vehicleOwnerInfo);
                            
                            vehicleInvestigationInfoSet.add(investigationVehicleInfo);
                            investigationTaskInfo.setVehicleInvestigationInfoSet(vehicleInvestigationInfoSet);
                            investigationTaskInfoes.add(investigationTaskInfo);
                            investigationInfo.setInvestigationTaskInfoes(investigationTaskInfoes);
                            
                            if (surveyInfo.getChakanLeixing() != null) {
                                
                                investigationInfo.setInvestigationType(surveyInfo.getChakanLeixing());
                            }
                            
                            if (surveyInfo.getChuxianJiashiyuan() != null) {
                                
                                investigationInfo.setAccidentDriver(surveyInfo.getChuxianJiashiyuan());
                            }
                            if (surveyInfo.getJiashizhenghao() != null) {
                                investigationInfo.setDrivingLicenseNo(surveyInfo.getJiashizhenghao());
                            }
                            if (surveyInfo.getZhunjiachexing() != null) {
                                investigationInfo.setPermitDriveVehicleModel(surveyInfo.getZhunjiachexing());
                            }
                            if (surveyInfo.getCheliangleibie() != null) {
                                
                                investigationInfo.setVehicleType(surveyInfo.getCheliangleibie());
                            }
                            if (surveyInfo.getJiashirenShifouBeibaoren() != null) {
                                if ("是".equals(surveyInfo.getJiashirenShifouBeibaoren())) {
                                    
                                    investigationInfo.setDriverIsTheInsured(true);
                                    
                                }else if ("否".equals(surveyInfo.getJiashirenShifouBeibaoren())) {
                                    
                                    investigationInfo.setDriverIsTheInsured(false);
                                }
                            }
                            
                            if (surveyInfo.getXingshizhengFazhengriqi() != null) {
                                
                                investigationInfo.setDrivingLicenseIssueDate(surveyInfo.getXingshizhengFazhengriqi());
                            }
                            if (surveyInfo.getXingshizhengZhuceriqi() != null) {
                                
                                investigationInfo.setDrivingLicenseRegisterDate(surveyInfo.getXingshizhengFazhengriqi());
                            }
                            
                            if (surveyInfo.getShifouBaoanChuxiandidian() != null) {
                                
                                if ("是".equals(surveyInfo.getShifouBaoanChuxiandidian())) {
                                    
                                    investigationInfo.setIsReportAccidentAddress(true);
                                    
                                }else if ("否".equals(surveyInfo.getShifouBaoanChuxiandidian())) {
                                    
                                    investigationInfo.setIsReportAccidentAddress(false);
                                }
                                
                            }
                            if (surveyInfo.getChuxiandidian() != null) {
                                
                                investigationInfo.setAccidentAddress(surveyInfo.getChuxiandidian());
                            }
                            
                            
                            
                            claimModel.setInvestigationInfo(investigationInfo);
                        }
                        //查勘信息   结束
                        
                        
                        
                        //保单信息   开始
                        List<String> baoDanList = userServiceI.getBaoDanListByClaimNumber(reportNo);
                        
                        if (baoDanList != null && baoDanList.size() > 0) {
                            
                            List<InsurancePolicy> insurancePolicies = new ArrayList<InsurancePolicy>();
                            
                            for (String baoDanHao : baoDanList) {
                                PolicyCategory banDanInfoByBaodanhao = userServiceI.getBanDanInfoByBaodanhao(baoDanHao);
                                if (banDanInfoByBaodanhao == null) {
                                    continue;
                                }
                                //单个保单信息
                                InsurancePolicy insurancePolicy = new InsurancePolicy();
                                
                                if (banDanInfoByBaodanhao.getBaodanhao() != null) {
                                    
                                    insurancePolicy.setPolicyNumber(banDanInfoByBaodanhao.getBaodanhao());
                                }
                                if (banDanInfoByBaodanhao.getBaodanLeixing() != null) {
                                    insurancePolicy.setPolicyNumberCategory(banDanInfoByBaodanhao.getBaodanLeixing());
                                }
                                //被保险人客户类型
                                if (banDanInfoByBaodanhao.getBeibaoxianrenKehuleixing() != null) {
                                    insurancePolicy.setTheInsuredType(banDanInfoByBaodanhao.getBeibaoxianrenKehuleixing());
                                }
                                //所属性质
                                if (banDanInfoByBaodanhao.getSuoshuxingzhi() != null) {
                                    insurancePolicy.setBelongToProperty(banDanInfoByBaodanhao.getSuoshuxingzhi());
                                }
                                //起保时间
                                if (banDanInfoByBaodanhao.getQibaoShijian() != null) {
                                    insurancePolicy.setInsuranceBeginDate(banDanInfoByBaodanhao.getQibaoShijian());
                                }
                                //终宝时间
                                if (banDanInfoByBaodanhao.getZhongbaoShijian() != null) {
                                    insurancePolicy.setInsuranceEndDate(banDanInfoByBaodanhao.getZhongbaoShijian());
                                }
                                if (banDanInfoByBaodanhao.getToubaorenMingcheng() != null) {
                                    insurancePolicy.setApplicantName(banDanInfoByBaodanhao.getToubaorenMingcheng());
                                }
                                if (banDanInfoByBaodanhao.getZhidingJiashiyuanxingming() != null) {
                                    insurancePolicy.setDesinatedDriverName(banDanInfoByBaodanhao.getZhidingJiashiyuanxingming());
                                }
                                //出单机构
                                if (banDanInfoByBaodanhao.getChudanjigou() != null) {
                                    insurancePolicy.setPolicyIssuingOrg(banDanInfoByBaodanhao.getChudanjigou());
                                }
                                //批改时间
                                if (banDanInfoByBaodanhao.getPigaishijian() != null) {
                                    Set<PolicyChangeLog> changeLogs = new HashSet<PolicyChangeLog>();
                                    PolicyChangeLog policyChangeLog = new PolicyChangeLog();
                                    try {
                                        policyChangeLog.setModificationTime(sdf.parse(banDanInfoByBaodanhao.getPigaishijian()));
                                        changeLogs.add(policyChangeLog);
                                        insurancePolicy.setChangeLogs(changeLogs);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                
                                //车辆信息：使用性质、行驶区域、是否投保新增设备
                                PolicyVehicleInfo policyVehicleInfo = new PolicyVehicleInfo();
                                //使用性质
                                if (banDanInfoByBaodanhao.getShiyongxingzhi() != null) {
                                    
                                    policyVehicleInfo.setUsageName(banDanInfoByBaodanhao.getShiyongxingzhi());
                                }
                                
                                //行驶区域
                                if (banDanInfoByBaodanhao.getXingshiquyu() != null) {
                                      
                                    policyVehicleInfo.setDrivingAreaName(banDanInfoByBaodanhao.getXingshiquyu());
                                }
                                
                                //是否投保新增设备
                                if (banDanInfoByBaodanhao.getShifouToubaoxinzengshebei() != null) {
                                    
                                    if ("是".equals(banDanInfoByBaodanhao.getShifouToubaoxinzengshebei())) {
                                        policyVehicleInfo.setIsNewEquipmentInsured(true);
                                    }else if ("否".equals(banDanInfoByBaodanhao.getShifouToubaoxinzengshebei())) {
                                        policyVehicleInfo.setIsNewEquipmentInsured(false);
                                    }
                                }
                                insurancePolicy.setPolicyVehicleInfo(policyVehicleInfo);
                                
                                //保险险别信息  开始
                                List<PolicyRiskclass> baoxianXianbieInfo = userServiceI.getBaoxianXianbieInfo(baoDanHao);
                                if (baoxianXianbieInfo != null && baoxianXianbieInfo.size() > 0) {
                                    //保险险别列表
                                    List<PolicyRiskCoverage> policyRiskCoverages = new ArrayList<PolicyRiskCoverage>();
                                    for (PolicyRiskclass policyRiskclass : baoxianXianbieInfo) {
                                        //单个保险险别
                                        PolicyRiskCoverage policyRiskCoverage = new PolicyRiskCoverage();
                                        if (policyRiskclass.getBaodanhao() != null) {
                                            policyRiskCoverage.setPolicyBillNo(policyRiskclass.getBaodanhao());
                                        }
                                        if (policyRiskclass.getXianbiemingcheng() != null) {
                                            policyRiskCoverage.setRiskClassName(policyRiskclass.getXianbiemingcheng());
                                        }
                                        if (policyRiskclass.getQibaoShijian() != null) {
                                            
                                            policyRiskCoverage.setInsuranceStartDate(policyRiskclass.getQibaoShijian());
                                        }
                                        if (policyRiskclass.getZhongbaoShijian() != null) {
                                            
                                            policyRiskCoverage.setInsuranceEndDate(policyRiskclass.getZhongbaoShijian());
                                        }
                                        if (policyRiskclass.getMianpeilv() != null) {
                                            
                                            policyRiskCoverage.setDeductibleRate(policyRiskclass.getMianpeilv().floatValue());
                                        }
                                        if (policyRiskclass.getMianpeie() != null) {
                                            policyRiskCoverage.setDeductible(policyRiskclass.getMianpeie().floatValue());
                                        }
                                        policyRiskCoverages.add(policyRiskCoverage);
                                    }
                                    
                                    insurancePolicy.setPolicyCoveredRisks(policyRiskCoverages);
                                }
                                //保险险别信息  结束
                                
                                //历史陪案列表  开始
                                List<HistorycaseInfo> lishiPeianLiebiao = userServiceI.getLishiPeianLiebiao(baoDanHao);
                                if (lishiPeianLiebiao != null && lishiPeianLiebiao.size() > 0) {
                                    //历史陪案列表
                                    List<PolicyHistoryClaimSettlement> historyClaimSettlements = new ArrayList<PolicyHistoryClaimSettlement>();
                                    for (HistorycaseInfo historycaseInfo : lishiPeianLiebiao) {
                                        //单个历史陪案
                                        PolicyHistoryClaimSettlement policyHistoryClaimSettlement = new PolicyHistoryClaimSettlement();
                                        
                                        if (historycaseInfo.getPeianhao() != null) {
                                            policyHistoryClaimSettlement.setClaimNumber(historycaseInfo.getPeianhao());
                                        }
                                        if (historycaseInfo.getBaoanren() != null) {
                                            
                                            policyHistoryClaimSettlement.setReporter(historycaseInfo.getBaoanren());
                                        }
                                        if (historycaseInfo.getBaoanDianhua() != null) {
                                            
                                            policyHistoryClaimSettlement.setReporterContact(historycaseInfo.getBaoanDianhua());
                                        }
                                        
                                        if (historycaseInfo.getBoanShijian() != null) {
                                            
                                            policyHistoryClaimSettlement.setReportTime(historycaseInfo.getBoanShijian());
                                        }
                                        if (historycaseInfo.getJiashiyuan() != null) {
                                            
                                            policyHistoryClaimSettlement.setDriver(historycaseInfo.getJiashiyuan());
                                        }
                                        if (historycaseInfo.getChepaihao() != null) {
                                            
                                            policyHistoryClaimSettlement.setRegistrationNumber(historycaseInfo.getChepaihao());
                                        }
                                        if (historycaseInfo.getChuxianShijian() != null) {
                                            
                                            policyHistoryClaimSettlement.setAccidentDate(historycaseInfo.getChuxianShijian());
                                        }
                                        if (historycaseInfo.getChuxianYuanyin() != null) {
                                            policyHistoryClaimSettlement.setAccidentReason(historycaseInfo.getChuxianYuanyin());
                                        }
                                        if (historycaseInfo.getJieanzhuangtai() != null) {
                                            policyHistoryClaimSettlement.setSettlementStatus(historycaseInfo.getJieanzhuangtai());
                                        }
                                        if (historycaseInfo.getPeifuShijian() != null) {
                                            policyHistoryClaimSettlement.setSettlementTime(historycaseInfo.getPeifuShijian());
                                        }
                                        //赔付险种名称
                                        if (historycaseInfo.getPeifuXianzhongmingcheng() != null) {
                                            InsuranceTypeInfo insuranceTypeInfo = new InsuranceTypeInfo();
                                            insuranceTypeInfo.setInsuranceTypeName(historycaseInfo.getPeifuXianzhongmingcheng());
                                            policyHistoryClaimSettlement.setInsuranceTypeInfo(insuranceTypeInfo);
                                        }
                                        if (historycaseInfo.getShifouYousanzheche() != null) {
                                            if ("是".equals(historycaseInfo.getShifouYousanzheche())) {
                                                
                                                policyHistoryClaimSettlement.setHasThirdPartyVehicles(true);
                                                
                                            }else if ("否".equals(historycaseInfo.getShifouYousanzheche())) {
                                                
                                                policyHistoryClaimSettlement.setHasThirdPartyVehicles(false);
                                                
                                            }
                                            
                                        }
                                        
                                        //三者车相关信息
                                        Set<BasicVehicleInfo> thirdPartyVehicles = new HashSet<BasicVehicleInfo>();
                                        BasicVehicleInfo basicVehicleInfo = new BasicVehicleInfo();
                                        if (historycaseInfo.getSanzhecheChelianghao() != null) {
                                            basicVehicleInfo.setRegistrationNumber(historycaseInfo.getSanzhecheChelianghao());
                                        }
                                        if (historycaseInfo.getSanzhecheVin() != null) {
                                            basicVehicleInfo.setVIN(historycaseInfo.getSanzhecheVin());
                                        }
                                        if (historycaseInfo.getSanzhecheChexing() != null) {
                                            basicVehicleInfo.setModel(historycaseInfo.getSanzhecheChexing());
                                        }
                                        thirdPartyVehicles.add(basicVehicleInfo);
                                        policyHistoryClaimSettlement.setThirdPartyVehicles(thirdPartyVehicles);
                                        
                                        //修理厂信息
                                        RepairFactoryInfo repairFactoryInfoHis = new RepairFactoryInfo();
                                        if (historycaseInfo.getXiulichangMingcheng() != null) {
                                            repairFactoryInfoHis.setName(historycaseInfo.getXiulichangMingcheng());
                                        }
                                        if (historycaseInfo.getXiulichangLeixing() != null) {
                                            repairFactoryInfoHis.setTypeName(historycaseInfo.getXiulichangLeixing());
                                        }
                                        policyHistoryClaimSettlement.setRepairFactoryInfo(repairFactoryInfoHis);
                                        
                                        historyClaimSettlements.add(policyHistoryClaimSettlement);
                                    }
                                    insurancePolicy.setHistoryClaimSettlements(historyClaimSettlements);
                                }
                                //历史陪案列表  开始
                                
                                //将单个保单信息放到保单列表中
                                insurancePolicies.add(insurancePolicy);
                            }
                            
                            //保单信息列表放到claimModel中
                            claimModel.setInsurancePolicies(insurancePolicies);
                        }
                        
                        //保单信息   结束
                        
                        lossAssessmentInfos.add(lossAssessmentInfo);
                        claimModel.setLossAssessmentInfoes(lossAssessmentInfos);
                        String claimJson = null;
                        claimJson = getJsonMapper().writeValueAsString(claimModel);
                        writeJson(claimJson, reportNo + chepaihao);
                        
                    }
                }
                
             }
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

    }
	
	@Test
	public void methodsTest(){
//	    List<ClaimReportInfo> claimReportInfos = userServiceI.getReportInfo("R020105222016003003");
//	    System.out.println(claimReportInfos.size());
	    
//        Lossaccessment lossaccessment = userServiceI.getLossInfoByClaimAndVehicleNumber("R020105222016003003", "鲁A23992");
//        System.out.println(lossaccessment.getChepaihao());
//
//        List<Lossaccessment> lossaccessments = userServiceI.getLossInfo("R020105222016003003");
//        System.out.println(lossaccessments.size());
        
//        LossaccessmentLabors lossaccessmentLabors = userServiceI.getLossLaborByClaimAndVehicleNumber("R190105242016000001", "鲁A29922");
//        System.out.println(lossaccessmentLabors.getGongshiMingcheng());
        

//	    List<LossaccessmentSpareparts> lossaccessmentSpareparts = userServiceI.getSparepartsByClaimAndVehicleNumber("R110705082016000002", "黑FSA3322");
//        System.out.println(lossaccessmentSpareparts.size());
	    
//	    LossaccessmentCasualites lossaccessmentCasualites = userServiceI.getLossaccessmentCasualitesByClaimnumber("R110705082016000002");
//	    System.out.println(lossaccessmentCasualites.getShangwangLeibie());
	    
//	    LossaccessmentVehicle vehicleInfoByClaimAndBrandNumber = userServiceI.getVehicleInfoByClaimAndBrandNumber("R080105222016000004","鲁HH5821");
//	    System.out.println(vehicleInfoByClaimAndBrandNumber.getBaoanhao());
	    
//        List<Reportinfo> reportinfos = userServiceI.getReportInfoByClaimNumber("R020105082016000002");
//        System.out.println(reportinfos.size());	  
//	    SurveyInfo surveyInfo = userServiceI.getSurveyInfoByClaimAndVehicleNumber("R190105222016000102", "京P152525");
//	    System.out.println(surveyInfo.getChakanBaogao());
	    
//	    LossaccessmentOpinions lossOptions = userServiceI.getLossOptionsByClaimAndVehicleNumber("80105072016000003", "鲁A00329");
//	    System.out.println(lossOptions.getHesunren());
//	    List<String> baoDanListByClaimNumber = userServiceI.getBaoDanListByClaimNumber("R020105082016000002");
//	    System.out.println(baoDanListByClaimNumber.get(1));
	    
//	    PolicyCategory banDanInfoByBaodanhao = userServiceI.getBanDanInfoByBaodanhao("1080105072013000004YD");
//	    System.out.println(banDanInfoByBaodanhao.getBaodanLeixing());
	    
	    List<PolicyRiskclass> baoxianXianbieInfo = userServiceI.getBaoxianXianbieInfo("1020105072015000005YD");
	    System.out.println(baoxianXianbieInfo.size());
	    
//	    List<HistorycaseInfo> lishiPeianLiebiao = userServiceI.getLishiPeianLiebiao("1110705072015400055YD");
//	    System.out.println(lishiPeianLiebiao.size());
//	    List<String> distinctReportNo = userServiceI.getDistinctReportNo();
//	    System.out.println(distinctReportNo.size());
	    
	}
	
	@After
	public void after(){

	}
	
	
	@Test
	public void testWriteJson(){
	    ClaimModel claimModel = new ClaimModel();
	    String claimJson;
        try {
            claimJson = getJsonMapper().writeValueAsString(claimModel);
            String fileName = "test";
            writeJson(claimJson, fileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
	    
	}
	
	/**
	 * 
	 * @param claimJson
	 * @param fileName
	 */
    public static void writeJson(String claimJson, String fileName) {
        // try {
        // String separator = File.separator;
        // String directory = "F:" + separator + "BaiduYunDownload" + separator
        // + "claim-taihe" + separator + "json" ;
        // String fileName1 = fileName + ".json";
        //
        // File file = new File(directory,fileName1);
        //
        // if (!file.exists()) {
        // // 先创建文件所在的目录
        // file.getParentFile().mkdirs();
        // try {
        // // 创建新文件
        // file.createNewFile();
        // } catch (IOException e) {
        // System.out.println("创建新文件时出现了错误。。。");
        // e.printStackTrace();
        // }
        // }
        //
        // FileWriter fileWritter = new FileWriter(file.getName(), false);
        // BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        // bufferWritter.write(claimJson);
        // bufferWritter.close();
        // System.out.println("Done");
        //
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        try {

            String separator = File.separator;
            String directory = "F:" + separator + "BaiduYunDownload" + separator + "claim-taihe" + separator + "json";
            String fileName1 = fileName + ".json";

            File file = new File(directory, fileName1);
            if (!file.exists()) {
                // 先创建文件所在的目录
                file.getParentFile().mkdirs();
                try {
                    // 创建新文件
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("创建新文件时出现了错误。。。");
                    e.printStackTrace();
                }
            }

            FileOutputStream osw = new FileOutputStream(file);
            byte[] b = new byte[1000];
            b = claimJson.getBytes();
            osw.write(b);
            osw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	@Test
	public void writeJson2(){
        try {

            // String separator = File.separator;
            // String directory = "F:" + separator + "BaiduYunDownload" +
            // separator + "claim-taihe" + separator + "json" ;
            // String fileName = "a";
            // String fileName1 = fileName + ".json";
            // String claimJson = "创建新文件时出现了错误。。。";
            //
            // File file = new File(directory,fileName1);
            //
            // if (!file.exists()) {
            // // 先创建文件所在的目录
            // file.getParentFile().mkdirs();
            // try {
            // // 创建新文件
            // file.createNewFile();
            // } catch (IOException e) {
            // System.out.println("创建新文件时出现了错误。。。");
            // e.printStackTrace();
            // }
            // }
            //
            // FileWriter fileWritter = new FileWriter(file.getName(), false);
            // BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            // bufferWritter.write(claimJson);
            // bufferWritter.close();
            // System.out.println("Done");

            String separator = File.separator;
            String directory = "F:" + separator + "BaiduYunDownload" + separator + "claim-taihe" + separator + "json";
            String fileName = "a";
            String fileName1 = fileName + ".json";
            String claimJson = "创建新文件时出现了错误。。。";

            File file = new File(directory, fileName1);
            if (!file.exists()) {
                // 先创建文件所在的目录
                file.getParentFile().mkdirs();
                try {
                    // 创建新文件
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("创建新文件时出现了错误。。。");
                    e.printStackTrace();
                }
            }

            FileOutputStream osw = new FileOutputStream(file);
            byte[] b = new byte[1000];
            b = claimJson.getBytes();
            osw.write(b);
            osw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}


}
