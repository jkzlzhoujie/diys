package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.VoteRecordDto;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface WeixinVoteRecordDao extends SimpleDao<VoteRecord, Long> {

	
	public List<VoteRecord> getSumCountByType(Map<String, Object> map);
	
	public List<VoteRecord> getSumCountPiao(Map<String, Object> map);
	
	public List<VoteRecord> getSumCountCall(Map<String, Object> map);
	
	public Page<VoteRecord> getSupportMeVoteRecordPage(Page<VoteRecord> page, Map<String, Object> parameter);
	
	public Page<VoteRecord> getISupportNetRedVoteRecordPage(Page<VoteRecord> page, Map<String, Object> parameter);
	
	public Page<VoteRecord> findBySupportNetRedPage(Page<VoteRecord> page, Map<String, Object> parameter);
	
	
}
