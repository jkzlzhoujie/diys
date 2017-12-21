package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.CmCircleUserFollow;
import cn.temobi.core.dao.SimpleDao;

public interface CmCircleUserFollowDao extends SimpleDao<CmCircleUserFollow, Long>{

	public List<Long> findUserId(Map<String, Object> map);

}
