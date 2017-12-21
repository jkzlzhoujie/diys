package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysPrivilegeContentTypeDao;
import cn.temobi.complex.entity.SysPrivilegeContentType;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("sysPrivilegeContentTypeDao")
public class SysPrivilegeContentTypeDaoImpl extends SimpleMybatisSupport<SysPrivilegeContentType, Long> implements SysPrivilegeContentTypeDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysPrivilegeContentType";
	}


	
}
