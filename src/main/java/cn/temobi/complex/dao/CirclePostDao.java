package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.CirclePost;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface CirclePostDao extends SimpleDao<CirclePost, Long>{

	Page findByPageTwo(Page page, Map<String, String> map);

}
