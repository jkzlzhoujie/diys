package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.WeixinUserInfoDao;
import cn.temobi.complex.entity.WeixinUserInfo;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("weixinUserInfoDao")
public class WeixinUserInfoDaoImpl extends SimpleMybatisSupport<WeixinUserInfo, Long> implements WeixinUserInfoDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.WeixinUserInfo";
	}


	
}
