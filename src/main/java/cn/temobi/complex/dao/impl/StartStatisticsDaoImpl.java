package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.StartStatisticsDao;
import cn.temobi.complex.dto.CountDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.entity.StartStatistics;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("startStatisticsDao")
public class StartStatisticsDaoImpl extends SimpleMybatisSupport<StartStatistics, Long> implements StartStatisticsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.StartStatistics";
	}

	@Override
	public String findMaxTime(Map<String, Object> map) {
		return  (String) this.getSqlSession().selectOne(toMybatisStatement("findMaxTime"), map);
	}
    
}
