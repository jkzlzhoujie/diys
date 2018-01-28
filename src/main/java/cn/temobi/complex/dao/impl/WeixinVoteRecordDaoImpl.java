package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import cn.temobi.complex.dao.WeixinVoteRecordDao;
import cn.temobi.complex.dto.VoteRecordDto;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("weixinVoteRecordDao")
public class WeixinVoteRecordDaoImpl extends SimpleMybatisSupport<VoteRecord, Long> implements WeixinVoteRecordDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.VoteRecord";
	}

	@Override
	public List<VoteRecord> getSumCountByType(Map<String, Object> parameter) {
		List<VoteRecord> voteRecords =  getSqlSession().selectList(toMybatisStatement("getSumCountByType"), parameter);
		return voteRecords;
	}
	
	@Override
	public List<VoteRecord> getSumCountPiao(Map<String, Object> parameter) {
		List<VoteRecord> voteRecords =  getSqlSession().selectList(toMybatisStatement("getSumCountPiao"), parameter);
		return voteRecords;
	}
	
	@Override
	public List<VoteRecord> getSumCountCall(Map<String, Object> parameter) {
		List<VoteRecord> voteRecords =  getSqlSession().selectList(toMybatisStatement("getSumCountCall"), parameter);
		return voteRecords;
	}

	@Override
	public Page<VoteRecord> getSupportMeVoteRecordPage(Page<VoteRecord> page,Map<String, Object> parameter) {
        List list = getSqlSession().selectList(toMybatisStatement("getSupportMeVoteRecordPage"), toParameterMap(parameter, page));
        page.setResult(list);
        return page;
	}

	@Override
	public Page<VoteRecord> getISupportNetRedVoteRecordPage(Page<VoteRecord> page,Map<String, Object> parameter) {
        List list = getSqlSession().selectList(toMybatisStatement("getISupportNetRedVoteRecordPage"), toParameterMap(parameter, page));
        page.setResult(list);
        return page;
	}
	
	@Override
	public Page<VoteRecord> findBySupportNetRedPage(Page<VoteRecord> page,Map<String, Object> parameter) {
        List list = getSqlSession().selectList(toMybatisStatement("findBySupportNetRedPage"), toParameterMap(parameter, page));
        page.setResult(list);
        return page;
	}
}
