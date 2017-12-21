package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.CmCircle;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface CmCircleDao extends SimpleDao<CmCircle, Long>{


	public Long check(Object parameter);

	public List<CmCircle> findMyFollow(Map<String, Object> map);

	public Page<CmCircle> findByPageTwo(Page page, Object map);

	public List<CmCircle> getByCircleIdAboutMe(Map<String, Object> map);

}
