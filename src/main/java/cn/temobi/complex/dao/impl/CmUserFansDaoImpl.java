package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserFansDao;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.entity.CmUserFans;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserFansDao")
public class CmUserFansDaoImpl extends SimpleMybatisSupport<CmUserFans, Long> implements CmUserFansDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserFans";
	}

	@Override
	public List<CmUserInfoListDto> findDto(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findDto"), map);
	}

}
