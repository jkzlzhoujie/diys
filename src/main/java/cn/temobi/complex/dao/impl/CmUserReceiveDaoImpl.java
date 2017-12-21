package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserReceiveDao;
import cn.temobi.complex.entity.CmUserReceive;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("cmUserReceiveDao")
public class CmUserReceiveDaoImpl extends SimpleMybatisSupport<CmUserReceive, Long> implements CmUserReceiveDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserReceive";
	}
	
	
}
