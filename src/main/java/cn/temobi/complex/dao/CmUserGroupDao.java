package cn.temobi.complex.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.temobi.complex.entity.CmUserGroup;
import cn.temobi.core.dao.SimpleDao;

public interface CmUserGroupDao extends SimpleDao<CmUserGroup, Long>{

	int deleteByGroupId(Object param) throws DataAccessException;

	public int updateByGroupId(Map map) throws DataAccessException;

}
