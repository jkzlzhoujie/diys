package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.CmBusiScope;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface CmBusiScopeDao extends SimpleDao<CmBusiScope, Long> {

	public void deleteByUserId(Map<String, String> map);
}
