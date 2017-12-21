package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.ProductRecommend;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface ProductRecommendDao extends SimpleDao<ProductRecommend,Long>{

	Page<ProductRecommend> findByPageTwo(Page page, Map<String, String> map);

}
