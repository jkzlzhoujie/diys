package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserRankingDao;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserRanking;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserRankingDao")
public class CmUserRankingDaoImpl extends SimpleMybatisSupport<CmUserRanking, Long> implements CmUserRankingDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserRanking";
	}

	@Override
	public void executeSql(List<CmUserInfo> list) {
		this.getSqlSession().selectOne(toMybatisStatement("executeSql"),list);
	}
	
	@Override
	public void executeSqlStrat() {
		this.getSqlSession().selectOne(toMybatisStatement("executeSqlStrat"));
	}
	
	@Override
	public void executeSqlEnd() {
		this.getSqlSession().selectOne(toMybatisStatement("executeSqlEnd"));
	}

	@Override
	public Number maxNum(Map<String,Object> map) {
		return (Number) this.getSqlSession().selectOne(toMybatisStatement("maxNum"),map);
	}
}
