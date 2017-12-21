package cn.temobi.complex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserReceiveDao;
import cn.temobi.complex.entity.CmUserReceive;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserReceiveService")
public class CmUserReceiveService extends ServiceBase{

	@Resource(name = "cmUserReceiveDao")
	private CmUserReceiveDao cmUserReceiveDao;
	
	public int update(CmUserReceive cmUserReceive){
		return cmUserReceiveDao.update(cmUserReceive);
	}
	
	public Page<CmUserReceive> findByPage(Page page,Object map){
		return cmUserReceiveDao.findByPage(page, map);
	}
	
	public List<CmUserReceive> findAll(){
		return cmUserReceiveDao.findAll();
	}
	
	public List<CmUserReceive> findByMap(Map param){
		return cmUserReceiveDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmUserReceiveDao.getCount(map);
	}
	
	public CmUserReceive getById(Long id){
		return cmUserReceiveDao.getById(id);
	}
	
	public int save(CmUserReceive cmUserReceive){
		return cmUserReceiveDao.save(cmUserReceive);
	}
	
	public int delete(Object id){
		return cmUserReceiveDao.delete(id);
	}

	public CmUserReceive setDefault(String userId,String userReceiveId) {
		Map<String,String> map = new HashMap<String, String>();
    	map.put("isDefault", "1");
    	map.put("userId", userId);
    	List<CmUserReceive> cmUserReceiveList = cmUserReceiveDao.findByMap(map);
    	if(cmUserReceiveList!=null && cmUserReceiveList.size()>0){
    		for (CmUserReceive cmUserReceiveOld : cmUserReceiveList) {
    			cmUserReceiveOld.setIsDefault(0);
    			cmUserReceiveDao.update(cmUserReceiveOld);
			}
    	}
		CmUserReceive cmUserReceive = cmUserReceiveDao.getById(Long.parseLong(userReceiveId));
		cmUserReceive.setIsDefault(1);
		cmUserReceiveDao.update(cmUserReceive);
		return cmUserReceive;
	}
}
