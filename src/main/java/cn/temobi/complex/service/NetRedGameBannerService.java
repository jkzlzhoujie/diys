package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.NetRedGameBannerDao;
import cn.temobi.complex.entity.NetRedGameBanner;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("netRedGameBannerService")
public class NetRedGameBannerService extends ServiceBase{

	@Resource(name = "netRedGameBannerDao")
	private NetRedGameBannerDao netRedGameBannerDao;
	
	public int update(NetRedGameBanner netRedGameBanner){
		return netRedGameBannerDao.update(netRedGameBanner);
	}
	
	public Page<NetRedGameBanner> findByPage(Page page,Object map){
		return netRedGameBannerDao.findByPage(page, map);
	}
	
	public Page<NetRedGameBanner> findBySupportNetRedPage(Page page,Object map){
		return netRedGameBannerDao.findByPage(page, map);
	}
	
	public List<NetRedGameBanner> findAll(){
		return netRedGameBannerDao.findAll();
	}
	
	public List<NetRedGameBanner> findByMap(Map param){
		return netRedGameBannerDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return netRedGameBannerDao.getCount(map);
	}
	
	public NetRedGameBanner getById(Long id){
		return netRedGameBannerDao.getById(id);
	}
	
	public int save(NetRedGameBanner netRedGameBanner){
		return netRedGameBannerDao.save(netRedGameBanner);
	}
	
	public int delete(Object id){
		return netRedGameBannerDao.delete(id);
	}
	
}
