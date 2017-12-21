package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmDesignerProductFormatDao;
import cn.temobi.complex.entity.CmDesignerProductFormat;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("cmDesignerProductFormatDao")
public class CmDesignerProductFormatDaoImpl extends SimpleMybatisSupport<CmDesignerProductFormat, Long> implements CmDesignerProductFormatDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmDesignerProductFormat";
	}

	@Override
	public void saveBatch(List<CmDesignerProductFormat> cmDesignerProductFormatList) {
		getSqlSession().insert(toMybatisStatement("insertBatch"), cmDesignerProductFormatList);
	}

	@Override
	public void deleteByProductId(Long productId) {
		getSqlSession().delete(toMybatisStatement("deleteByProductId"), productId);
	}
	
	

}
