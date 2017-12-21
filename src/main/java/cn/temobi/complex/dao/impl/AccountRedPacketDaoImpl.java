package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.AccountRedPacketDao;
import cn.temobi.complex.entity.AccountRedPacket;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("accountRedPacketDao")
public class AccountRedPacketDaoImpl extends SimpleMybatisSupport<AccountRedPacket, Long> implements AccountRedPacketDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccountRedPacket";
	}

	@Override
	public List<Long> findAllJoin(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findAllJoin"), map);
	}

}
