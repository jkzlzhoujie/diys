package cn.temobi.complex.dao;

import cn.temobi.complex.dto.OrderDto;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.core.dao.SimpleDao;

public interface PmCommodityInfoDao extends SimpleDao<PmCommodityInfo,Long>{

	public OrderDto getDtoById(Long id);
}
