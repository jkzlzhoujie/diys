package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserAttentionsDao;
import cn.temobi.complex.entity.CmUserAttentions;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserAttentionsDao")
public class CmUserAttentionsDaoImpl extends SimpleMybatisSupport<CmUserAttentions, Long> implements CmUserAttentionsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserAttentions";
	}

}
