package cn.temobi.complex.dao;

import cn.temobi.complex.entity.BlackListUserEquipment;
import cn.temobi.core.dao.SimpleDao;

public interface BlackListUserEquipmentDao extends SimpleDao<BlackListUserEquipment, Long>{

	public Number checkContent(Object parameter);
}
