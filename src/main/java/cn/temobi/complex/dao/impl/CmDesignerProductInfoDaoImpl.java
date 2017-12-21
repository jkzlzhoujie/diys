package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmDesignerProductInfoDao;
import cn.temobi.complex.entity.CmDesignerProductInfo;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("cmDesignerProductInfoDao")
public class CmDesignerProductInfoDaoImpl extends SimpleMybatisSupport<CmDesignerProductInfo, Long> implements CmDesignerProductInfoDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmDesignerProductInfo";
	}
	
}
