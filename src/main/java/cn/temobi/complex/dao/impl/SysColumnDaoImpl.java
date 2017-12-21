package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysColumnDao;
import cn.temobi.complex.entity.SysColumn;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysColumnDao")
public class SysColumnDaoImpl extends SimpleMybatisSupport<SysColumn, Long> implements SysColumnDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysColumn";
	}

}
