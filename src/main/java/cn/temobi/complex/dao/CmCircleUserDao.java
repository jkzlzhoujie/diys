package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.CmUserInfoListDto;
import org.springframework.dao.DataAccessException;
import cn.temobi.complex.entity.CmCircleUser;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface CmCircleUserDao extends SimpleDao<CmCircleUser, Long>{

	public Page<CmUserInfoListDto> findUser(Page<CmUserInfoListDto> page, Object parameter);
	public Long check(Object parameter);
	
	public Long delBycircleId(Object id) throws DataAccessException;
	
	public void insertList(Map<String,Object> map);
	
	public void upByCircleId(Map map);
	
	public List<Long> findUserId(Object parameter);
	
	public Page findByPageTwo(Page page, Map<String, String> map);
}
