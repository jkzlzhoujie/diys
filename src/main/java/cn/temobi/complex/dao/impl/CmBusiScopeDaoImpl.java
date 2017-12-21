package cn.temobi.complex.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmBusiScopeDao;
import cn.temobi.complex.entity.CmBusiScope;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmBusiScopeDao")
public class CmBusiScopeDaoImpl extends SimpleMybatisSupport<CmBusiScope, Long> implements CmBusiScopeDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmBusiScope";
	}

	@Override
	public void deleteByUserId(Map<String, String> map) {
		this.getSqlSession().delete(toMybatisStatement("deleteByUserId"), map);
	}

}
