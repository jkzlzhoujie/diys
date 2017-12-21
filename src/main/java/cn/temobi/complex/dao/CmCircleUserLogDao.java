package cn.temobi.complex.dao;

import org.springframework.dao.DataAccessException;

import cn.temobi.complex.entity.CmCircleUserLog;
import cn.temobi.core.dao.SimpleDao;

public interface CmCircleUserLogDao extends SimpleDao<CmCircleUserLog, Long>{

	public Number check(Object parameter);
	
	public Long delBycircleId(Object id) throws DataAccessException;
}
