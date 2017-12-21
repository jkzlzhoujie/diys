package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysPrivilegeContentDao;
import cn.temobi.complex.entity.SysPrivilegeContent;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("sysPrivilegeContentDao")
public class SysPrivilegeContentDaoImpl extends SimpleMybatisSupport<SysPrivilegeContent, Long> implements SysPrivilegeContentDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysPrivilegeContent";
	}

	@Override
	public int deleteByPrivilegeId(String privilegeId) {
		return getSqlSession().delete(toMybatisStatement("deleteByPrivilegeId"), privilegeId);
	}
	

	
}
