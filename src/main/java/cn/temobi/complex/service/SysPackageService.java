package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountUserBuyDao;
import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.dao.CmUserGroupDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.OrderDao;
import cn.temobi.complex.dao.PmCommodityInfoDao;
import cn.temobi.complex.dao.SysPackageDao;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.CmUserGroup;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.SysPackage;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.BigDecimalUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysPackageService")
public class SysPackageService extends ServiceBase{

	@Resource(name = "sysPackageDao")
	private SysPackageDao sysPackageDao;
	
	@Resource(name = "orderDao")
	private OrderDao orderDao;
	
	@Resource(name="cmUserGroupDao")
	private CmUserGroupDao cmUserGroupDao;
	
	@Resource(name="accountUserBuyDao")
	private AccountUserBuyDao accountUserBuyDao;
	
	@Resource(name="pmCommodityInfoDao")
	private PmCommodityInfoDao pmCommodityInfoDao;
	
	@Resource(name="accountWalletLogDao")
	private AccountWalletLogDao accountWalletLogDao;
	
	@Resource(name="accountUserDao")
	private AccountUserDao accountUserDao;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name="cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	public int update(SysPackage sysPackage){
		return sysPackageDao.update(sysPackage);
	}
	
	public Page<SysPackage> findByPage(Page page,Object map){
		return sysPackageDao.findByPage(page, map);
	}
	
	public List<SysPackage> findAll(){
		return sysPackageDao.findAll();
	}
	
	public List<SysPackage> findByMap(Map param){
		return sysPackageDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return sysPackageDao.getCount(map);
	}
	
	public SysPackage getById(Long id){
		return sysPackageDao.getById(id);
	}
	
	public int save(SysPackage sysPackage){
		return sysPackageDao.save(sysPackage);
	}
	
	public int delete(Object id){
		return sysPackageDao.delete(id);
	}

}
