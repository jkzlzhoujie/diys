package cn.temobi.complex.dao;

import cn.temobi.complex.entity.SysPrivilegePackage;
import cn.temobi.core.dao.SimpleDao;

public interface SysPrivilegePackageDao extends SimpleDao<SysPrivilegePackage, Long>{

	public int deleteByPrivilegeId(String privilegeId);

}
