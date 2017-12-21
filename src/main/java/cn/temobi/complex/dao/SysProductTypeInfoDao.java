package cn.temobi.complex.dao;


import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.AllTypeDto;
import cn.temobi.complex.entity.SysProductTypeInfo;
import cn.temobi.core.dao.SimpleDao;

public interface SysProductTypeInfoDao extends SimpleDao<SysProductTypeInfo, Long> {

	public List<SysProductTypeInfo> findByUserId(Map<String,Object> map);
	
	public List<AllTypeDto> findAllType(Map<String,Object> map);
}
