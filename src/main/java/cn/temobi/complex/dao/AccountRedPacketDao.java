package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.AccountRedPacket;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface AccountRedPacketDao extends SimpleDao<AccountRedPacket, Long> {

	public List<Long> findAllJoin(Map<String, Object> map);
}
