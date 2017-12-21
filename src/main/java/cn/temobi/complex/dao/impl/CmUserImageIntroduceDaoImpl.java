package cn.temobi.complex.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserImageIntroduceDao;
import cn.temobi.complex.entity.CmUserImageIntroduce;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserImageIntroduceDao")
public class CmUserImageIntroduceDaoImpl extends SimpleMybatisSupport<CmUserImageIntroduce, Long> implements CmUserImageIntroduceDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserImageIntroduce";
	}

	@Override
	public void deleteByUserId(Map<String, String> map) {
		this.getSqlSession().delete(toMybatisStatement("deleteByUserId"), map);
	}

}
