package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CirclePostPicDao;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("circlePostPicDao")
public class CirclePostPicDaoImpl extends SimpleMybatisSupport<CirclePostPic, Long> implements CirclePostPicDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CirclePostPic";
	}

	@Override
	public void saveBatch(List<CirclePostPic> circlePostPicList) {
		getSqlSession().insert(toMybatisStatement("insertBatch"), circlePostPicList);
		
	}


	
}
