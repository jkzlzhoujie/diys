package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SystemRightsDao;
import cn.temobi.complex.entity.SystemRights;
import cn.temobi.core.common.SimpleMybatisSupport;

/**
 * 客户端用户账户信息
 * @author hujf
 *
 */
@Component
@Repository("systemRightsDao")
public class SystemRightsDaoImpl extends SimpleMybatisSupport<SystemRights, Long> implements SystemRightsDao{
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SystemRights";
	}
    
}
