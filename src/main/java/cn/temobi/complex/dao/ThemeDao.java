package cn.temobi.complex.dao;


import java.util.Map;

import cn.temobi.complex.entity.Theme;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 17 17:15:32
 */
public interface ThemeDao extends SimpleDao<Theme, Long> {

	public Number sumLove(Map<String, String> map);
	
}
