package cn.temobi.complex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountRedPacketLogDao;
import cn.temobi.complex.entity.AccountRedPacketLog;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("accountRedPacketLogService")
public class AccountRedPacketLogService extends ServiceBase{
	
	@Resource(name = "accountRedPacketLogDao")
	private AccountRedPacketLogDao accountRedPacketLogDao;
	
	public Number sumPrice(Map map){
		return accountRedPacketLogDao.sumPrice(map);
	} 
	
	public int update(AccountRedPacketLog entity){
		return accountRedPacketLogDao.update(entity);
	}
	
	public Page<AccountRedPacketLog> findByPage(Page page,Object map){
		return accountRedPacketLogDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return accountRedPacketLogDao.getCount(map);
	}
	
	public AccountRedPacketLog getById(Long id){
		return accountRedPacketLogDao.getById(id);
	}
	
	public int save(AccountRedPacketLog entity){
		return accountRedPacketLogDao.save(entity);
	}
	
	public int delete(Object id){
		return accountRedPacketLogDao.delete(id);
	}
	
	public int delete(AccountRedPacketLog entity){
		return accountRedPacketLogDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return accountRedPacketLogDao.findByMap(map);
	}
    
	public List<AccountRedPacketLog> findAll(){
		return accountRedPacketLogDao.findAll();
	}
	
	public List<AccountRedPacketLog> findAll(AccountRedPacketLog entity){
		return accountRedPacketLogDao.findAll(entity);
	}
	
	public ResponseObject myRedLog(Map<String,Object> passMap,ResponseObject object){
		String pageNo =  passMap.get("pageNo").toString();
		String pageSize =  passMap.get("pageSize").toString();
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		Map<String,Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("userId", cmUserInfo.getId());
    	Page<AccountRedPacketLog> pageResult = accountRedPacketLogDao.findByPage(page, searchMap);
		List<AccountRedPacketLog> redLogList = pageResult.getResult();
		Map<String,Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("redLogList", redLogList);
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
}
