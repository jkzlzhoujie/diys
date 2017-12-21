package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserFriendsDao;
import cn.temobi.complex.entity.CmUserFriends;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserFriendsDao")
public class CmUserFriendsDaoImpl extends SimpleMybatisSupport<CmUserFriends, Long> implements CmUserFriendsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserFriends";
	}

}
