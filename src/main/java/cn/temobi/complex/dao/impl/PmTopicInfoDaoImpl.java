package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmTopicInfoDao;
import cn.temobi.complex.entity.PmTopicInfo;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmTopicInfoDao")
public class PmTopicInfoDaoImpl extends SimpleMybatisSupport<PmTopicInfo, Long> implements PmTopicInfoDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmTopicInfo";
	}

}
