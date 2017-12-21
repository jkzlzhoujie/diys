package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.QqListDao;
import cn.temobi.complex.entity.QqList;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("qqListDao")
public class QqListDaoImpl extends SimpleMybatisSupport<QqList, Long> implements QqListDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.QqList";
	}
	
	
}
