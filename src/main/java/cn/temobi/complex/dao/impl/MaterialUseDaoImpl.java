package cn.temobi.complex.dao.impl;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.MaterialUseDao;
import cn.temobi.complex.entity.MaterialUse;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("materialUseDao")
public class MaterialUseDaoImpl extends SimpleMybatisSupport<MaterialUse, Long> implements MaterialUseDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.MaterialUse";
	}

}
