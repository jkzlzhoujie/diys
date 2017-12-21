package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.MaterialDao;
import cn.temobi.complex.dao.MaterialDownloadDao;
import cn.temobi.complex.entity.Material;
import cn.temobi.complex.entity.MaterialDownload;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 客户端下载统计管理
 * @author hjf
 * 2014 三月 26 09:40:28
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("materialDownloadService")
public class MaterialDownloadService extends ServiceBase{
	
	@Resource(name = "materialDownloadDao")
	private MaterialDownloadDao materialDownloadDao;
	
	@Resource(name = "materialDao")
	private MaterialDao materialDao;
	
	public int update(MaterialDownload entity){
		return materialDownloadDao.update(entity);
	}
	
	public int save(MaterialDownload entity){
		return materialDownloadDao.save(entity);
	}
	
	public Page<MaterialDownload> findByPage(Page page,Object map){
		return materialDownloadDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return materialDownloadDao.getCount(map);
	}
	
	public MaterialDownload getById(Long id){
		return materialDownloadDao.getById(id);
	}
	
	
	public int delete(Object id){
		return materialDownloadDao.delete(id);
	}
	
	public int delete(MaterialDownload entity){
		return materialDownloadDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return materialDownloadDao.findByMap(map);
	}
    
	public List<MaterialDownload> findAll(){
		return materialDownloadDao.findAll();
	}
	
	public List<MaterialDownload> findAll(MaterialDownload entity){
		return materialDownloadDao.findAll(entity);
	}

	public void saveUpdate(MaterialDownload entity,Material material){
		materialDownloadDao.save(entity);
		materialDao.update(material);
	}
	
}
