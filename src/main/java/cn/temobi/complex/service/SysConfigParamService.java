package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysConfigParamDao;
import cn.temobi.complex.dao.SysTypeTimeDao;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SysTypeTime;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysConfigParamService")
public class SysConfigParamService extends ServiceBase{
	
	@Resource(name = "sysConfigParamDao")
	private SysConfigParamDao sysConfigParamDao;
	
	@Resource(name = "sysTypeTimeDao")
	private SysTypeTimeDao sysTypeTimeDao;
	
	public int update(SysConfigParam entity){
		return sysConfigParamDao.update(entity);
	}
	
	public Page<SysConfigParam> findByPage(Page page,Object map){
		return sysConfigParamDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysConfigParamDao.getCount(map);
	}
	
	public SysConfigParam getById(Long id){
		return sysConfigParamDao.getById(id);
	}
	
	public int save(SysConfigParam entity){
		return sysConfigParamDao.save(entity);
	}
	
	public int delete(Object id){
		return sysConfigParamDao.delete(id);
	}
	
	public int delete(SysConfigParam entity){
		return sysConfigParamDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysConfigParamDao.findByMap(map);
	}
    
	public List<SysConfigParam> findAll(){
		return sysConfigParamDao.findAll();
	}
	
	public List<SysConfigParam> findAll(SysConfigParam entity){
		return sysConfigParamDao.findAll(entity);
	}
	
	
	public ResponseObject index(Map<String,Object> passMap,ResponseObject object){
		
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", "1");
		searchMap.put("status", "1");
		searchMap.put("currentDate", DateUtils.getCurrDateStr());
		List<SysTypeTime> timeList = sysTypeTimeDao.findByMap(searchMap);
		List<SysConfigParam> anlist = new ArrayList<SysConfigParam>();
		if(timeList != null && timeList.size() > 0)
		{
			SysTypeTime sysTypeTime = timeList.get(0);
			searchMap.put("type", "2");
			searchMap.put("expand1", sysTypeTime.getId());
			anlist = sysConfigParamDao.findByMap(searchMap);
			if(anlist != null && anlist.size() > 0)
			{
				for(SysConfigParam sysConfigParam:anlist){
					sysConfigParam.setParamValue(host+sysConfigParam.getParamValue());
				}
			}
		}
		
		searchMap.remove("expand1");
		searchMap.put("type", "3");
		List<SysConfigParam> levellist = sysConfigParamDao.findByMap(searchMap);
		if(levellist != null && levellist.size() > 0)
		{
			for(SysConfigParam sysConfigParam:levellist){
				sysConfigParam.setParamValue(host+sysConfigParam.getParamValue());
			}
		}
		
		searchMap.put("type", "4");
		List<SysConfigParam> helplist = sysConfigParamDao.findByMap(searchMap);
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("anlist", anlist);
		returnMap.put("levellist", levellist);
		returnMap.put("helplist", helplist);
		
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
}
