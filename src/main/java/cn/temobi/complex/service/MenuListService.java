package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.MenuListDao;
import cn.temobi.complex.entity.MenuList;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("menuListService")
public class MenuListService extends ServiceBase{

	@Resource(name = "menuListDao")
	private MenuListDao menuListDao;
	
	public int update(MenuList menulist){
		return menuListDao.update(menulist);
	}
	
	public Page<MenuList> findByPage(Page page,Object map){
		return menuListDao.findByPage(page, map);
	}
	
	public List<MenuList> findAll(){
		return menuListDao.findAll();
	}
	
	public List<MenuList> findByMap(Map param){
		return menuListDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return menuListDao.getCount(map);
	}
	
	public MenuList getById(Long id){
		return menuListDao.getById(id);
	}
	
	public int save(MenuList menulist){
		return menuListDao.save(menulist);
	}
}
