package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysFlowNoDao;
import cn.temobi.complex.entity.SysFlowNo;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysFlowNoDao")
public class SysFlowNoDaoImpl extends SimpleMybatisSupport<SysFlowNo, Long> implements SysFlowNoDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysFlowNo";
	}

}
