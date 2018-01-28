package cn.temobi.complex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.WeixinVoteRecordDao;
import cn.temobi.complex.dto.VoteRecordDto;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("weixinVoteRecordService")
public class WeixinVoteRecordService extends ServiceBase{

	@Resource(name = "weixinVoteRecordDao")
	private WeixinVoteRecordDao WeixinVoteRecordDao;
	
	public int update(VoteRecord voteRecord){
		return WeixinVoteRecordDao.update(voteRecord);
	}
	
	public Page<VoteRecord> findByPage(Page page,Object map){
		return WeixinVoteRecordDao.findByPage(page, map);
	}
	
	public List<VoteRecord> findAll(){
		return WeixinVoteRecordDao.findAll();
	}
	
	public List<VoteRecord> findByMap(Map param){
		return WeixinVoteRecordDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return WeixinVoteRecordDao.getCount(map);
	}
	
	public VoteRecord getById(Long id){
		return WeixinVoteRecordDao.getById(id);
	}
	
	public int save(VoteRecord voteRecord){
		return WeixinVoteRecordDao.save(voteRecord);
	}
	
	public int delete(Object id){
		return WeixinVoteRecordDao.delete(id);
	}
	
	public List<VoteRecord> getSumCountByType(Map<String, Object> param){
		
		return WeixinVoteRecordDao.getSumCountByType(param);
	}
	
	public List<VoteRecord> getSumCountPiao(Map<String, Object> param){
		return WeixinVoteRecordDao.getSumCountPiao(param);
	}

	public List<VoteRecord> getSumCountCall(Map<String, Object> param){
		return WeixinVoteRecordDao.getSumCountCall(param);
	}
	
	public Page<VoteRecord> getSupportMeVoteRecordPage(Page<VoteRecord> page,Map<String, Object> param){
		return WeixinVoteRecordDao.getSupportMeVoteRecordPage(page, param);
	}
	
	public Page<VoteRecord> getISupportNetRedVoteRecordPage(Page<VoteRecord> page,Map<String, Object> param){
		return WeixinVoteRecordDao.getISupportNetRedVoteRecordPage(page, param);
	}
	
	public Page<VoteRecord> findBySupportNetRedPage(Page<VoteRecord> page,Map<String, Object> param){
		return WeixinVoteRecordDao.findBySupportNetRedPage(page, param);
	}
	
	
	public ResponseObject saveVote(ResponseObject object ,String voteUserId, String netRedUserId,String count, String type) {
		
		
		
		VoteRecord voteRecord = new VoteRecord();
		int countNum = Integer.valueOf(count);
		if( type.trim().equals("2")){
			voteRecord.setCount(10 * countNum);
			voteRecord.setCallCount(countNum);
		}else{
			voteRecord.setCount(countNum);
			voteRecord.setCallCount(0);
			
			//判断今天是否已经投了五次
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("voteUserId", voteUserId);
			param.put("netRedUserId", netRedUserId);
			param.put("createTime", "yes");
			List<VoteRecord> voteRecords = WeixinVoteRecordDao.findByMap(param);
			if(voteRecords !=null && voteRecords.size()>=5){
				object.setCode("moreFive");
	    		object.setDesc("亲！你今天的免费票已用完，可以继续为他打CALL助力哦！");
	    		return object;
			}
			
		}
		voteRecord.setNetRedUserId(Long.valueOf(netRedUserId));
		voteRecord.setVoteUserId(Long.valueOf(voteUserId));
		voteRecord.setType(Integer.valueOf(type));
		int num = WeixinVoteRecordDao.save(voteRecord);
		if(num > 0){
    		object.setCode("success");
	    	object.setDesc("投票成功！");
    	}else{
    		object.setCode("fail");
    		object.setDesc("投票失败！");
    	}
		return object;
	}
	
}
