package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.BannerDao;
import cn.temobi.complex.entity.Banner;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("bannerDao")
public class BannerDaoImpl extends SimpleMybatisSupport<Banner, Long> implements BannerDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Banner";
	}
    
}
