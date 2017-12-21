package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserExtendInfoDao;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserExtendInfoDao")
public class CmUserExtendInfoDaoImpl extends SimpleMybatisSupport<CmUserExtendInfo, Long> implements CmUserExtendInfoDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserExtendInfo";
	}

}
