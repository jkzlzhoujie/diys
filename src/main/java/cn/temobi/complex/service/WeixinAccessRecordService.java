package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.WeixinAccessRecordDao;
import cn.temobi.complex.entity.AccessRecord;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("weixinAccessRecordService")
public class WeixinAccessRecordService extends ServiceBase{

	@Resource(name = "weixinAccessRecordDao")
	private WeixinAccessRecordDao weixinAccessRecordDao;
	
	public int update(AccessRecord accessRecord){
		return weixinAccessRecordDao.update(accessRecord);
	}
	
	public Page<AccessRecord> findByPage(Page page,Object map){
		return weixinAccessRecordDao.findByPage(page, map);
	}
	
	public Page<AccessRecord> findBySupportNetRedPage(Page page,Object map){
		return weixinAccessRecordDao.findByPage(page, map);
	}
	
	public List<AccessRecord> findAll(){
		return weixinAccessRecordDao.findAll();
	}
	
	public List<AccessRecord> findByMap(Map param){
		return weixinAccessRecordDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return weixinAccessRecordDao.getCount(map);
	}
	
	public AccessRecord getById(Long id){
		return weixinAccessRecordDao.getById(id);
	}
	
	public int save(AccessRecord accessRecord){
		return weixinAccessRecordDao.save(accessRecord);
	}
	
	public int delete(Object id){
		return weixinAccessRecordDao.delete(id);
	}
}
