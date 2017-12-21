package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmScoreLogSeeDao;
import cn.temobi.complex.dto.CountScoreDto;
import cn.temobi.complex.entity.PmScoreLogSee;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmScoreLogSeeDao")
public class PmScoreLogSeeDaoImpl extends SimpleMybatisSupport<PmScoreLogSee, Long> implements PmScoreLogSeeDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmScoreLogSee";
	}

	@Override
	public List<CountScoreDto> findCountByUser(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findCountByUser"), map);
	}
}
