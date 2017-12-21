package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.entity.CmUserFans;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 17 17:15:32
 */
public interface CmUserFansDao extends SimpleDao<CmUserFans, Long> {

	public List<CmUserInfoListDto> findDto(Map<String, Object> map);
}
