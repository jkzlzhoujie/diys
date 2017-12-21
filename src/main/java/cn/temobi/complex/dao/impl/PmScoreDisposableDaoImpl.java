package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmScoreDisposableDao;
import cn.temobi.complex.entity.PmScoreDisposable;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmScoreDisposableDao")
public class PmScoreDisposableDaoImpl extends SimpleMybatisSupport<PmScoreDisposable, Long> implements PmScoreDisposableDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmScoreDisposable";
	}
}
