package cn.temobi.complex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.CmUserRankingDao;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserRanking;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.PageModel;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserRankingService")
public class CmUserRankingService extends ServiceBase{
	
	private static Logger log = LoggerFactory.getLogger(CmUserRankingService.class);
	
	@Resource(name = "cmUserRankingDao")
	private CmUserRankingDao cmUserRankingDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	public int update(CmUserRanking entity){
		return cmUserRankingDao.update(entity);
	}
	
	public void executeSql(){
		cmUserRankingDao.executeSqlStrat();
		Map<String,Object> map = new HashMap<String, Object>();
		Number totalItems = cmUserInfoDao.getCount(map);
		Page page  =  new Page();
		page.setTotalItems(totalItems.longValue());
		page.setPageSize(1000);
		System.out.println(page.getTotalPages());
		int totalSize = 0;
		for(int i=1;i<=page.getTotalPages();i++){
			page.setPageNo(i);
			map.put("offset", page.getOffset());
		    map.put("limit", page.getPageSize());
		    List sblist = cmUserInfoDao.findByPage(map);
			totalSize+=sblist.size();
			cmUserRankingDao.executeSql(sblist);
		   
		}
		log.error("成功插入数据="+totalSize);
		cmUserRankingDao.executeSqlEnd();
//		if(list != null && list.size() > 0)
//		{
//			int totalSize = 0;
//			PageModel pageModel = new PageModel(list, 1000);
//			log.error("排名分页插入开始执行");
//			for(int i=1;i<=pageModel.getTotalPages();i++){
//				List sblist = pageModel.getObjects(i);
//				totalSize+=sblist.size();
//				cmUserRankingDao.executeSql(sblist);
//			}
//			log.error("成功插入数据="+totalSize);
//		}
//		cmUserRankingDao.executeSqlEnd();
	}
	
	public Page<CmUserRanking> findByPage(Page page,Object map){
		return cmUserRankingDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserRankingDao.getCount(map);
	}
	
	public Number maxNum(Map map){
		return cmUserRankingDao.maxNum(map);
	}
	
	public CmUserRanking getById(Long id){
		return cmUserRankingDao.getById(id);
	}
	
	public int save(CmUserRanking entity){
		return cmUserRankingDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserRankingDao.delete(id);
	}
	
	public int delete(CmUserRanking entity){
		return cmUserRankingDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserRankingDao.findByMap(map);
	}
    
	public List<CmUserRanking> findAll(){
		return cmUserRankingDao.findAll();
	}

}
