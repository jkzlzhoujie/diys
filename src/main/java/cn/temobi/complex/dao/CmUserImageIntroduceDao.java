package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.CmUserImageIntroduce;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface CmUserImageIntroduceDao extends SimpleDao<CmUserImageIntroduce, Long> {

	public void deleteByUserId(Map<String, String> map);
}
