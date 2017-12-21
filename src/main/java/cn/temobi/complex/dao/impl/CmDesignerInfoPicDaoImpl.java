package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmDesignerInfoPicDao;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.complex.entity.CmDesignerInfoPic;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

import com.salim.cache.CacheHelper;


@Component
@Repository("cmDesignerInfoPicDao")
public class CmDesignerInfoPicDaoImpl extends SimpleMybatisSupport<CmDesignerInfoPic, Long> implements CmDesignerInfoPicDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmDesignerInfoPic";
	}

	@Override
	public void saveBatch(List<CmDesignerInfoPic> circlePostPicList) {
		getSqlSession().insert(toMybatisStatement("insertBatch"), circlePostPicList);
		
	}


	
}
