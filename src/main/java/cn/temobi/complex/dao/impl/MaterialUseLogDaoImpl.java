package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.MaterialUseLogDao;

import cn.temobi.complex.entity.MaterialUseLog;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("materialUseLogDao")
public class MaterialUseLogDaoImpl extends SimpleMybatisSupport<MaterialUseLog, Long> implements MaterialUseLogDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.MaterialUseLog";
	}

	@Override
	public void insertList(List<MaterialUseLog> list) {
		this.getSqlSession().insert(toMybatisStatement("insertList"), list);
		
	}
}
