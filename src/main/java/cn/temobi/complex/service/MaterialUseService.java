package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.MaterialUseDao;
import cn.temobi.complex.dto.MaterialUseDto;
import cn.temobi.complex.entity.MaterialUse;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("materialUseService")
public class MaterialUseService extends ServiceBase{

	@Resource(name = "materialUseDao")
	private MaterialUseDao materialUseDao;
	
	public Page<MaterialUseDto> findByPageDto(Page page,Object map){
		return materialUseDao.findByPage(page, "findByPageDto", map);
	}
	
	public Number getCount(Map map){
		return materialUseDao.getCount(map);
	}
	
	public MaterialUse getById(Long id){
		return materialUseDao.getById(id);
	}
	
	public int save(MaterialUse entity){
		return materialUseDao.save(entity);
	}
	
	public int delete(Object id){
		return materialUseDao.delete(id);
	}

	public int delete(MaterialUse entity){
		return materialUseDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return materialUseDao.findByMap(map);
	}

	public List<MaterialUse> findAll(){
		return materialUseDao.findAll();
	}

	public int update(MaterialUse entity){
		return materialUseDao.update(entity);
	}
}
