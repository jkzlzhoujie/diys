package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmCommodityInfoDao;
import cn.temobi.complex.dto.OrderDto;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmCommodityInfoService")
public class PmCommodityInfoService extends ServiceBase{
	
	@Resource(name = "pmCommodityInfoDao")
	private PmCommodityInfoDao pmCommodityInfoDao;
	
	public int update(PmCommodityInfo entity){
		return pmCommodityInfoDao.update(entity);
	}
	
	public Page<PmCommodityInfo> findByPage(Page page,Object map){
		return pmCommodityInfoDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmCommodityInfoDao.getCount(map);
	}
	
	public PmCommodityInfo getById(Long id){
		return pmCommodityInfoDao.getById(id);
	}
	
	public OrderDto getDtoById(Long id){
		return pmCommodityInfoDao.getDtoById(id);
	}
	
	public int save(PmCommodityInfo entity){
		return pmCommodityInfoDao.save(entity);
	}
	
	public int delete(Object id){
		return pmCommodityInfoDao.delete(id);
	}
	
	public int delete(PmCommodityInfo entity){
		return pmCommodityInfoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmCommodityInfoDao.findByMap(map);
	}
    
	public List<PmCommodityInfo> findAll(){
		return pmCommodityInfoDao.findAll();
	}
	
	public List<PmCommodityInfo> findAll(PmCommodityInfo entity){
		return pmCommodityInfoDao.findAll(entity);
	}
}
