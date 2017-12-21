package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmTopicCaseDao;
import cn.temobi.complex.entity.PmTopicCase;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmTopicCaseDao")
public class PmTopicCaseDaoImpl extends SimpleMybatisSupport<PmTopicCase, Long> implements PmTopicCaseDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmTopicCase";
	}

}
