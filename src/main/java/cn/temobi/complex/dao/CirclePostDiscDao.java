package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.CirclePostDisc;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface CirclePostDiscDao extends SimpleDao<CirclePostDisc, Long>{

	Page findByPageTwo(Page page, Map<String, String> map);

}
