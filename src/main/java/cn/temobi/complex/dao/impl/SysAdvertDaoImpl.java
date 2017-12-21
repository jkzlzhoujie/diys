package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysAdvertDao;
import cn.temobi.complex.entity.SysAdvert;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysAdvertDao")
public class SysAdvertDaoImpl extends SimpleMybatisSupport<SysAdvert, Long> implements SysAdvertDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysAdvert";
	}
    
}
