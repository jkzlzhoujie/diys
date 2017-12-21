package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.CmCircleUserFollowDao;
import cn.temobi.complex.entity.CmCircleUserFollow;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmCircleUserFollowDao")
public class CmCircleUserFollowDaoImpl extends SimpleMybatisSupport<CmCircleUserFollow, Long> implements CmCircleUserFollowDao {

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmCircleUserFollow";
	}
	
	@Override
	public List<Long> findUserId(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		 return getSqlSession().selectList(toMybatisStatement("findUserId"), parameter);
	}
}
