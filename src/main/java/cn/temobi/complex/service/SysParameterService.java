package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysParameterDao;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysParameterService")
public class SysParameterService extends ServiceBase{

	@Resource(name = "sysParameterDao")
	private SysParameterDao sysParameterDao;
	
	public int update(SysParameter sysParameter){
		return sysParameterDao.update(sysParameter);
	}
	
	public Page<SysParameter> findByPage(Page page,Object map){
		return sysParameterDao.findByPage(page, map);
	}
	
	public List<SysParameter> findAll(){
		return sysParameterDao.findAll();
	}
	
	public List<SysParameter> findByMap(Map param){
		return sysParameterDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return sysParameterDao.getCount(map);
	}
	
	public SysParameter getById(Long id){
		return sysParameterDao.getById(id);
	}
	
	public int save(SysParameter sysParameter){
		return sysParameterDao.save(sysParameter);
	}
	
	public int delete(Object id){
		return sysParameterDao.delete(id);
	}
}
