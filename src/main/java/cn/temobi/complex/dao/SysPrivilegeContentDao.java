package cn.temobi.complex.dao;

import cn.temobi.complex.entity.SysPrivilegeContent;
import cn.temobi.core.dao.SimpleDao;

public interface SysPrivilegeContentDao extends SimpleDao<SysPrivilegeContent, Long>{

		public int deleteByPrivilegeId(String privilegeId);

}
