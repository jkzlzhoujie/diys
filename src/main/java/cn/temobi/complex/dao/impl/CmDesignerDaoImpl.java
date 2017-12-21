package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.CmDesignerDao;
import cn.temobi.complex.entity.CmDesignerInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("cmDesignerDao")
public class CmDesignerDaoImpl extends SimpleMybatisSupport<CmDesignerInfo, Long> implements CmDesignerDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmDesignerInfo";
	}
	
}
