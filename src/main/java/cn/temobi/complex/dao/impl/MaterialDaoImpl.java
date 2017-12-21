package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.MaterialDao;
import cn.temobi.complex.entity.Material;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("materialDao")
public class MaterialDaoImpl extends SimpleMybatisSupport<Material, Long> implements MaterialDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Material";
	}

	@Override
	public List<Material> findUseByMap(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findUseByMap"), map);
	}
}
