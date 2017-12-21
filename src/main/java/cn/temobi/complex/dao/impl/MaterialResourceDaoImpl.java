package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.MaterialResourceDao;
import cn.temobi.complex.entity.MaterialResource;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;
import cn.temobi.core.util.MybatisStatementUtils;

@Component
@Repository("materialResourceDao")
public class MaterialResourceDaoImpl extends SimpleMybatisSupport<MaterialResource, Long> implements MaterialResourceDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.MaterialResource";
	}

	@Override
	public Number maxId(Map<String, String> map) {
		 return (Number) getSqlSession().selectOne(toMybatisStatement("maxId"),map);
	}

	 public Number getCountTo(Object parameter) {
	        return (Number) getSqlSession().selectOne(toMybatisStatement("getCountTo"), toParameterMap(parameter));
	   }
	
	@Override
	public Page<MaterialResource> findByPageTo(Page<MaterialResource> page,
			Object parameter) {
		Number totalItems = getCountTo(parameter);
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findByPageTo"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}

}
