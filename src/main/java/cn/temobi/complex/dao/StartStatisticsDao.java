package cn.temobi.complex.dao;


import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.StartStatistics;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface StartStatisticsDao extends SimpleDao<StartStatistics, Long> {

	public String findMaxTime(Map<String, Object> map);
}
