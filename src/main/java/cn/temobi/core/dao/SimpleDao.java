package cn.temobi.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.temobi.core.common.Page;



/**
 * @author yingx
 * @created 2012-2-15
 * @package com.plaminfo.core.dao
 * @param <T>
 * @param <PK>
 */
public interface SimpleDao<T, PK extends Serializable> {

    /**
     * 根据ID获得对象
     *
     * @param id
     * @return
     * @throws org.springframework.dao.DataAccessException
     *
     */
    public T getById(PK id) throws DataAccessException;

    /**
     * 根据Id删除
     *
     * @param id
     * @throws DataAccessException
     */
    public int delete(PK id) throws DataAccessException;


    public int delete(Object parameter) throws DataAccessException;

    public int delete(String statementName, Object parameter) throws DataAccessException;

    /**
     * 插入数据
     *
     * @param entity
     * @throws DataAccessException
     */
    public int save(T entity) throws DataAccessException;

    /**
     * 更新数据
     *
     * @param entity
     * @throws DataAccessException
     */


    public int update(T entity) throws DataAccessException;

    /**
     * 根据id检查是否插入或是更新数据
     */
    public void saveOrUpdate(T entity) throws DataAccessException;

    public boolean isUnique(T entity, String uniquePropertyNames) throws DataAccessException;

    /**
     * 用于hibernate.flush() 有些dao实现不需要实现此类
     */
    public void flush() throws DataAccessException;

    public List<T> findAll() throws DataAccessException;

    public List<T> findAll(T entity) throws DataAccessException;

    public Number getCount(Object parameter) throws DataAccessException;

    public List<T> findByMap(Map<String, Object> param) throws DataAccessException;

    public List<T> findByMap(Object param) throws DataAccessException;

    public List<T> findByMap(String statementName, Object parameter) throws DataAccessException;

    public <T> Page<T> findByPage(Page<T> page, String statementName, Object parameter) throws DataAccessException;

    public <T> Page<T> findByPage(Page<T> page, Object parameter) throws DataAccessException;


}
