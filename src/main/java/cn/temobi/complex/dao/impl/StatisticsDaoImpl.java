package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.StatisticsDao;
import cn.temobi.complex.entity.Statistics;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("statisticsDao")
public class StatisticsDaoImpl extends SimpleMybatisSupport<Statistics, Long> implements StatisticsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Statistics";
	}
    
}
