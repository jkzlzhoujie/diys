package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.MyDiscussDto;
import cn.temobi.complex.dto.PmProductDiscussDto;
import cn.temobi.complex.dto.TwoDiscussDto;
import cn.temobi.complex.entity.PmProductDiscuss;
import cn.temobi.core.dao.SimpleDao;


public interface PmProductDiscussDao extends SimpleDao<PmProductDiscuss, Long> {

	public List<PmProductDiscussDto> findDto(Map<String, Object> map);
	
	public List<TwoDiscussDto> findDtoTo(Map<String, Object> map);
	
	public List<MyDiscussDto> findMyDiscuss(Map<String, Object> map);

	public Number findDiscussCount(Map<String, Object> map);

	public List<Long> findIdList(Map<String, Object> map);

}
