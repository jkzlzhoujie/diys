package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.ProvinceDao;
import cn.temobi.complex.entity.Province;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("provinceDao")
public class ProvinceDaoImpl extends SimpleMybatisSupport<Province, Long> implements ProvinceDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Province";
	}

	@Override
	public Province getBySegment(String segment) {
		return (Province) getSqlSession().selectOne(toMybatisStatement("getBySegment"), segment);
	}
    
}
