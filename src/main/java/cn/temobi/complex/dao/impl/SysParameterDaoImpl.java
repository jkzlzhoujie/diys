package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysParameterDao;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("sysParameterDao")
public class SysParameterDaoImpl extends SimpleMybatisSupport<SysParameter, Long> implements SysParameterDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysParameter";
	}


	
}
