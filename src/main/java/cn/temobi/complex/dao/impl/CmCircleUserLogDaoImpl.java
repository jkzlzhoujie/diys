package cn.temobi.complex.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.CmCircleUserLogDao;
import cn.temobi.complex.entity.CmCircleUserLog;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmCircleUserLogDao")
public class CmCircleUserLogDaoImpl extends SimpleMybatisSupport<CmCircleUserLog, Long> implements CmCircleUserLogDao {

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmCircleUserLog";
	}
	@Override
	public Number check(Object parameter){
		return (Number) getSqlSession().selectOne(toMybatisStatement("check"), toParameterMap(parameter));
	}
	
	@Override
    public Long delBycircleId(Object id) throws DataAccessException {
        return (Long)getSqlSession().selectOne(toMybatisStatement("delBycircleId"), id);
    }
}
