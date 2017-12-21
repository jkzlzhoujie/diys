package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserSignLogDao;
import cn.temobi.complex.dao.PmScoreDisposableDao;
import cn.temobi.complex.dao.PmScoreLogDao;
import cn.temobi.complex.dao.PmScoreLogSeeDao;
import cn.temobi.complex.dao.PmScoreLogSeeUserDao;
import cn.temobi.complex.dao.SysScoreDao;
import cn.temobi.complex.dto.CountScoreDto;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserSignLog;
import cn.temobi.complex.entity.PmScoreDisposable;
import cn.temobi.complex.entity.PmScoreLogSee;
import cn.temobi.complex.entity.PmScoreLogSeeUser;
import cn.temobi.complex.entity.SysScore;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;


@Transactional
@Service("sysScoreService")
public class SysScoreService extends ServiceBase{
	
	@Resource(name = "sysScoreDao")
	private SysScoreDao sysScoreDao;
	
	@Resource(name = "pmScoreDisposableDao")
	private PmScoreDisposableDao pmScoreDisposableDao;
	
	@Resource(name = "pmScoreLogDao")
	private PmScoreLogDao pmScoreLogDao;
	
	@Resource(name = "pmScoreLogSeeDao")
	private PmScoreLogSeeDao pmScoreLogSeeDao;
	
	@Resource(name = "pmScoreLogSeeUserDao")
	private PmScoreLogSeeUserDao pmScoreLogSeeUserDao;
	
	@Resource(name = "cmUserSignLogDao")
	private CmUserSignLogDao cmUserSignLogDao;
	
	public int update(SysScore sysScore){
		return sysScoreDao.update(sysScore);
	}
	
	public Page<SysScore> findByPage(Page page,Object map){
		return sysScoreDao.findByPage(page, map);
	}
	
	public List<SysScore> findAll(){
		return sysScoreDao.findAll();
	}
	
	public List<SysScore> findByMap(Map param){
		return sysScoreDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return sysScoreDao.getCount(map);
	}
	
	public SysScore getById(Long id){
		return sysScoreDao.getById(id);
	}
	
	public int save(SysScore sysScore){
		return sysScoreDao.save(sysScore);
	}
	
	public int delete(Object id){
		return sysScoreDao.delete(id);
	}

	
	public ResponseObject taskDisposableList(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("flag", "2");
		List<SysScore> list = sysScoreDao.findByMap(searchMap);
		searchMap.put("userId", cmUserInfo.getId());
		List<PmScoreDisposable> myScoreList = pmScoreDisposableDao.findByMap(searchMap);
		List<SysScore> returnList = new ArrayList<SysScore>();
		for(SysScore sysScore:list){
			boolean temp = false;
			Long type = sysScore.getId();
			if(myScoreList != null && myScoreList.size() > 0)
			{
				for(PmScoreDisposable pmScoreDisposable:myScoreList){
					if(type - pmScoreDisposable.getType() == 0)
					{
						temp = true;
						break;
					}
				}
			}
			if(!temp)
			{
				returnList.add(sysScore);
			}
		}
		object.setResponse(returnList);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public ResponseObject taskDayList(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Map<String,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("flag", "1");
		List<SysScore> list = sysScoreDao.findByMap(searchMap);
		searchMap.put("userId", cmUserInfo.getId());
		searchMap.put("time", DateUtils.getCurrDateStr());
		List<CountScoreDto> myScoreList = pmScoreLogDao.findCountByUser(searchMap);
		List<PmScoreLogSee> seeList = pmScoreLogSeeDao.findByMap(searchMap);
		List<PmScoreLogSeeUser> seeUserList = pmScoreLogSeeUserDao.findByMap(searchMap);
		List<CmUserSignLog> signlist = cmUserSignLogDao.findByMap(searchMap);
		for(SysScore sysScore:list){
			Long type = sysScore.getId();
			if(type - 12 == 0)
			{
				if(seeUserList != null && seeUserList.size() > 0)
				{
					sysScore.setDoneNum(seeUserList.size());
				}
			}else if(type - 14 == 0)
			{
				if(seeList != null && seeList.size() > 0)
				{
					sysScore.setDoneNum(seeList.size());
				}
			}else if(type == 0)
			{
				if(signlist != null && signlist.size() > 0)
				{
					sysScore.setDoneNum(signlist.size());
				}
			}
			else{
				if(myScoreList != null && myScoreList.size() > 0)
				{
					for(CountScoreDto countScoreDto:myScoreList){
						if(type - countScoreDto.getType() == 0)
						{
							sysScore.setDoneNum(countScoreDto.getNum());
							break;
						}
					}
				}
			}
		}
		object.setResponse(list);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
}
