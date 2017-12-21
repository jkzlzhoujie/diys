package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.BackgroundPicDao;
import cn.temobi.complex.entity.BackgroundPic;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("backgroundPicDao")
public class BackgroundPicDaoImpl extends SimpleMybatisSupport<BackgroundPic, Long> implements BackgroundPicDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.BackgroundPic";
	}


	
}
