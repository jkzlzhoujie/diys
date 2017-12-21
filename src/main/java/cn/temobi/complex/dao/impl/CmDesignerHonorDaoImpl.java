package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmDesignerHonorDao;
import cn.temobi.complex.entity.CmDesignerHonor;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("cmDesignerHonorDao")
public class CmDesignerHonorDaoImpl extends SimpleMybatisSupport<CmDesignerHonor, Long> implements CmDesignerHonorDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmDesignerHonor";
	}

	@Override
	public void saveBatch(List<CmDesignerHonor> cmDesignerHonorList) {
		getSqlSession().insert(toMybatisStatement("insertBatch"), cmDesignerHonorList);
		
	}

	
}
