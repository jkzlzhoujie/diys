package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserPrivilegeDao;
import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("cmUserPrivilegeDao")
public class CmUserPrivilegeDaoImpl extends SimpleMybatisSupport<CmUserPrivilege, Long> implements CmUserPrivilegeDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserPrivilege";
	}


	
}
