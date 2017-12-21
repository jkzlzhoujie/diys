package cn.temobi.core.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.util.ReflectionUtils;

import cn.temobi.core.dao.SimpleDao;
import cn.temobi.core.util.MybatisStatementUtils;



public abstract class SimpleMybatisSupport<T, PK extends Serializable> extends SqlSessionDaoSupport implements SimpleDao<T, PK> {
    @SuppressWarnings("unchecked")
	@Override
    public T getById(PK id) throws DataAccessException {
        return (T) getSqlSession().selectOne(toMybatisStatement(MybatisStatementUtils.getByIdStatement()), id);
    }

    @Override
    public int delete(PK id) throws DataAccessException {
        return getSqlSession().delete(toMybatisStatement(MybatisStatementUtils.getDeleteStatement()), id);
    }

    @Override
    public int delete(String statementName, Object parameter) throws DataAccessException {
        return getSqlSession().delete(toMybatisStatement(statementName), parameter);
    }

    @Override
    public int delete(Object parameter) throws DataAccessException {
        return getSqlSession().delete(toMybatisStatement(MybatisStatementUtils.getDeleteStatement()), parameter);
    }

    @Override
    public int save(T entity) throws DataAccessException {
        prepareObjectForSaveOrUpdate(entity);
        return getSqlSession().insert(toMybatisStatement(MybatisStatementUtils.getInsertStatement()), entity);
    }

    @Override
    public int update(T entity) throws DataAccessException {
        prepareObjectForSaveOrUpdate(entity);
        return getSqlSession().update(toMybatisStatement(MybatisStatementUtils.getUpdateStatement()), entity);
    }

    protected void prepareObjectForSaveOrUpdate(T entity) {

    }


    @Override
    public void saveOrUpdate(T entity) throws DataAccessException {

    }

    @Override
    public boolean isUnique(T entity, String uniquePropertyNames) throws DataAccessException {
        return false;
    }


    @Override
    public void flush() throws DataAccessException {
        this.getSqlSession().commit();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<T> findAll() throws DataAccessException {
        return getSqlSession().selectList(toMybatisStatement(MybatisStatementUtils.getFindAllStatement()));
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<T> findAll(T entity) throws DataAccessException {
        return getSqlSession().selectList(toMybatisStatement(MybatisStatementUtils.getFindAllStatement()), entity);
    }

    public Number getCount(Object parameter) {
        return (Number) getSqlSession().selectOne(toMybatisStatement(MybatisStatementUtils.getCountStatement()), toParameterMap(parameter));
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<T> findByMap(String statementName, Object parameter) throws DataAccessException {
        return getSqlSession().selectList(toMybatisStatement(statementName), toParameterMap(parameter));
    }

    @Override
    public List<T> findByMap(Map<String, Object> param) throws DataAccessException {
        return findByMap(MybatisStatementUtils.getFindByMapStatement(), param);
    }

    @Override
    public List<T> findByMap(Object param) throws DataAccessException {
        return findByMap(MybatisStatementUtils.getFindByMapStatement(), param);
    }

    @SuppressWarnings({ "unchecked", "hiding" })
	@Override
    public <T> Page<T> findByPage(Page<T> page, String statementName, Object parameter) throws DataAccessException {
    	Number totalItems = getCount(parameter);
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement(statementName), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
    }

    @SuppressWarnings("hiding")
	@Override
    public <T> Page<T> findByPage(Page<T> page, Object parameter) throws DataAccessException {
        return findByPage(page, MybatisStatementUtils.getFindByPageStatement(), parameter);
    }

    @SuppressWarnings("unchecked")
	protected static Map toParameterMap(Object parameter, Page p) {
        Map map = toParameterMap(parameter);
        map.put("startRow", p.getStartRow());
        map.put("endRow", p.getEndRow());
        map.put("offset", p.getOffset());
        map.put("limit", p.getPageSize());
        return map;
    }

    @SuppressWarnings("unchecked")
	protected static Map toParameterMap(Object parameter) {
        if (null == parameter) return new HashMap();
        if (parameter instanceof Map) {
            return (Map) parameter;
        } else {
            try {
                return PropertyUtils.describe(parameter);
            } catch (Exception e) {
                ReflectionUtils.handleReflectionException(e);
                return null;
            }
        }
    }

    public String toMybatisStatement(String statementName) {
        return getMybatisMapperNamesapce() + "." + statementName;
    }

    public String getMybatisMapperNamesapce() {
        throw new RuntimeException("请设置Mybatis 对应的持久对象命名空间！");
    }


}
