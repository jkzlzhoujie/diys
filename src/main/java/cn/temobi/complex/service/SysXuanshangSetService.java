package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysXuanshangSetDao;
import cn.temobi.complex.entity.SysXuanshangSet;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysXuanshangSetService")
public class SysXuanshangSetService extends ServiceBase{

	@Resource(name = "sysXuanshangSetDao")
	private SysXuanshangSetDao sysXuanshangSetDao;
	
	public int update(SysXuanshangSet menulist){
		return sysXuanshangSetDao.update(menulist);
	}
	
	public Page<SysXuanshangSet> findByPage(Page page,Object map){
		return sysXuanshangSetDao.findByPage(page, map);
	}
	
	public List<SysXuanshangSet> findAll(){
		return sysXuanshangSetDao.findAll();
	}
	
	public List<SysXuanshangSet> findByMap(Map param){
		return sysXuanshangSetDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return sysXuanshangSetDao.getCount(map);
	}
	
	public SysXuanshangSet getById(Long id){
		return sysXuanshangSetDao.getById(id);
	}
	
	public int save(SysXuanshangSet menulist){
		return sysXuanshangSetDao.save(menulist);
	}

	public void delete(long id) {
		sysXuanshangSetDao.delete(id);
	}
}
