package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysXuanshangSetDao;
import cn.temobi.complex.entity.SysXuanshangSet;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("sysXuanshangSetDao")
public class SysXuanshangSetDaoImpl extends SimpleMybatisSupport<SysXuanshangSet, Long> implements SysXuanshangSetDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysXuanshangSet";
	}


	
}
