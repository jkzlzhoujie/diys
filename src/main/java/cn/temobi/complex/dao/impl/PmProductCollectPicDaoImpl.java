package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmProductCollectPicDao;
import cn.temobi.complex.entity.PmProductCollectPic;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("pmProductCollectPicDao")
public class PmProductCollectPicDaoImpl extends SimpleMybatisSupport<PmProductCollectPic, Long> implements PmProductCollectPicDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmProductCollectPic";
	}

	@Override
	public void saveBatch(List<PmProductCollectPic> pmProductCollectPicList) {
		getSqlSession().insert(toMybatisStatement("insertBatch"), pmProductCollectPicList);
	}


	
}
