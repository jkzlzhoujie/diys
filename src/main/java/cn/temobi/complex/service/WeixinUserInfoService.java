package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.WeixinUserInfoDao;
import cn.temobi.complex.entity.WeixinUserInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("weixinUserInfoService")
public class WeixinUserInfoService extends ServiceBase{

	@Resource(name = "weixinUserInfoDao")
	private WeixinUserInfoDao weixinUserInfoDao;
	
	public int update(WeixinUserInfo weixinUserInfo){
		return weixinUserInfoDao.update(weixinUserInfo);
	}
	
	public Page<WeixinUserInfo> findByPage(Page page,Object map){
		return weixinUserInfoDao.findByPage(page, map);
	}
	
	public List<WeixinUserInfo> findAll(){
		return weixinUserInfoDao.findAll();
	}
	
	public List<WeixinUserInfo> findByMap(Map param){
		return weixinUserInfoDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return weixinUserInfoDao.getCount(map);
	}
	
	public WeixinUserInfo getById(Long id){
		return weixinUserInfoDao.getById(id);
	}
	
	public int save(WeixinUserInfo weixinUserInfo){
		return weixinUserInfoDao.save(weixinUserInfo);
	}
	
	public int delete(Object id){
		return weixinUserInfoDao.delete(id);
	}
}
