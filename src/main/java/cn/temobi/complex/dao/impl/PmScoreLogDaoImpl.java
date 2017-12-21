package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmScoreLogDao;
import cn.temobi.complex.dto.CountScoreDto;
import cn.temobi.complex.entity.PmScoreLog;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmScoreLogDao")
public class PmScoreLogDaoImpl extends SimpleMybatisSupport<PmScoreLog, Long> implements PmScoreLogDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmScoreLog";
	}

	@Override
	public List<CountScoreDto> findCountByUser(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findCountByUser"), map);
	}
}
