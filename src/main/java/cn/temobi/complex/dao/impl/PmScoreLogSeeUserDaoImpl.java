package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmScoreLogSeeUserDao;
import cn.temobi.complex.dto.CountScoreDto;
import cn.temobi.complex.entity.PmScoreLogSeeUser;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmScoreLogSeeUserDao")
public class PmScoreLogSeeUserDaoImpl extends SimpleMybatisSupport<PmScoreLogSeeUser, Long> implements PmScoreLogSeeUserDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmScoreLogSeeUser";
	}

	@Override
	public List<CountScoreDto> findCountByUser(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findCountByUser"), map);
	}
}
