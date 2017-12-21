package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.ResourceShareDao;
import cn.temobi.complex.dto.ShareCountDto;
import cn.temobi.complex.entity.ResourceShare;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("resourceShareDao")
public class ResourceShareDaoImpl extends SimpleMybatisSupport<ResourceShare, Long> implements ResourceShareDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.ResourceShare";
	}
	 
}
