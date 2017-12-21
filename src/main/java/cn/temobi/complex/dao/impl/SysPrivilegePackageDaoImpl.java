package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysPrivilegePackageDao;
import cn.temobi.complex.entity.SysPrivilegePackage;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("sysPrivilegePackageDao")
public class SysPrivilegePackageDaoImpl extends SimpleMybatisSupport<SysPrivilegePackage, Long> implements SysPrivilegePackageDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysPrivilegePackage";
	}

	@Override
	public int deleteByPrivilegeId(String privilegeId) {
		return getSqlSession().delete(toMybatisStatement("deleteByPrivilegeId"), privilegeId);
	}
	

	
}
