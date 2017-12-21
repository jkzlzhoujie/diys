package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmFriendsRequestDao;
import cn.temobi.complex.entity.CmFriendsRequest;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmFriendsRequestDao")
public class CmFriendsRequestDaoImpl extends SimpleMybatisSupport<CmFriendsRequest, Long> implements CmFriendsRequestDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmFriendsRequest";
	}

}
