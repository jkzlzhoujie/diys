package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.BlackListUserEquipmentDao;
import cn.temobi.complex.entity.BlackListUserEquipment;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("blackListUserEquipmentService")
public class BlackListUserEquipmentService extends ServiceBase{

	@Resource(name = "blackListUserEquipmentDao")
	private BlackListUserEquipmentDao blackListUserEquipmentDao;
	
	public Number checkContent(Map map){
		return blackListUserEquipmentDao.checkContent(map);
	}
	
	public int update(BlackListUserEquipment blackListUserEquipment){
		return blackListUserEquipmentDao.update(blackListUserEquipment);
	}
	
	public Page<BlackListUserEquipment> findByPage(Page page,Object map){
		return blackListUserEquipmentDao.findByPage(page, map);
	}
	
	public List<BlackListUserEquipment> findAll(){
		return blackListUserEquipmentDao.findAll();
	}
	
	public List<BlackListUserEquipment> findByMap(Map param){
		return blackListUserEquipmentDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return blackListUserEquipmentDao.getCount(map);
	}
	
	public BlackListUserEquipment getById(Long id){
		return blackListUserEquipmentDao.getById(id);
	}
	
	public int save(BlackListUserEquipment blackListUserEquipment){
		return blackListUserEquipmentDao.save(blackListUserEquipment);
	}
	
	public int delete(Object id){
		return blackListUserEquipmentDao.delete(id);
	}
}
