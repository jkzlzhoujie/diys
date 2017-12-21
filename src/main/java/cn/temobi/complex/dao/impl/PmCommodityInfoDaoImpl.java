package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmCommodityInfoDao;
import cn.temobi.complex.dto.OrderDto;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("pmCommodityInfoDao")
public class PmCommodityInfoDaoImpl extends SimpleMybatisSupport<PmCommodityInfo,Long> implements PmCommodityInfoDao{
	
	@Override
	public String getMybatisMapperNamesapce(){
		return "cn.temobi.complex.entity.PmCommodityInfo";
	}

	@Override
	public OrderDto getDtoById(Long id) {
		return (OrderDto) this.getSqlSession().selectOne(toMybatisStatement("getDtoById"), id);
	}
}
