package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.VoteRecordDao;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("voteRecordDao")
public class VoteRecordDaoImpl extends SimpleMybatisSupport<VoteRecord, Long> implements VoteRecordDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.VoteRecord";
	}


	
}
