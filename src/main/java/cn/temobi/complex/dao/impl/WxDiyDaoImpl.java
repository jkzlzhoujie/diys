package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.WxDiyDao;
import cn.temobi.complex.entity.WxDiy;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("wxDiyDao")
public class WxDiyDaoImpl extends SimpleMybatisSupport<WxDiy, Long> implements WxDiyDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.WxDiy";
	}

}
