package cn.temobi.complex.dao.impl;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CouponMobileDao;
import cn.temobi.complex.entity.CouponMobile;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("couponMobileDao")
public class CouponMobileDaoImpl extends SimpleMybatisSupport<CouponMobile, Long> implements CouponMobileDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CouponMobile";
	}

}
