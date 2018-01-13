package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.NetRedGameBannerDao;
import cn.temobi.complex.entity.NetRedGameBanner;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("netRedGameBannerDao")
public class NetRedGameBannerDaoImpl extends SimpleMybatisSupport<NetRedGameBanner, Long> implements NetRedGameBannerDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.NetRedGameBanner";
	}


	
}
