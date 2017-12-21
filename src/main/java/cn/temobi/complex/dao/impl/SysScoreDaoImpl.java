package cn.temobi.complex.dao.impl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysScoreDao;

import cn.temobi.complex.entity.SysScore;
import cn.temobi.core.common.SimpleMybatisSupport;
@Component
@Repository("sysScoreDao")
public class SysScoreDaoImpl extends SimpleMybatisSupport<SysScore, Long> implements SysScoreDao{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysScore";
	}
}
