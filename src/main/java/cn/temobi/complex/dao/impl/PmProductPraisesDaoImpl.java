package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmProductPraisesDao;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmProductPraisesDao")
public class PmProductPraisesDaoImpl extends SimpleMybatisSupport<PmProductPraises, Long> implements PmProductPraisesDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmProductPraises";
	}

	@Override
	public List<Long> findIdList(Map<String, Object> map) {
		return  this.getSqlSession().selectList(toMybatisStatement("findIdList"), map);
	}
}
