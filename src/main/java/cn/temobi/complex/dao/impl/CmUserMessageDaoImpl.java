package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserMessageDao;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserMessageDao")
public class CmUserMessageDaoImpl extends SimpleMybatisSupport<CmUserMessage, Long> implements CmUserMessageDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserMessage";
	}

}
