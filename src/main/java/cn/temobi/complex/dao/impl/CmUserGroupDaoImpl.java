package cn.temobi.complex.dao.impl;



import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.CmUserGroupDao;

import cn.temobi.complex.entity.CmUserGroup;

import cn.temobi.core.common.SimpleMybatisSupport;
import cn.temobi.core.util.MybatisStatementUtils;

@Component
@Repository("cmUserGroupDao")
public class CmUserGroupDaoImpl extends SimpleMybatisSupport<CmUserGroup, Long> implements CmUserGroupDao{
	
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserGroup";
	}
	@Override
    public int deleteByGroupId(Object param) throws DataAccessException {
        return  getSqlSession().delete(toMybatisStatement("deleteByGroupId"), param);
    }
	
    @Override
    public int updateByGroupId(Map map) throws DataAccessException {
        return getSqlSession().update(toMybatisStatement("updateByGroupId"), map);
    }
}
