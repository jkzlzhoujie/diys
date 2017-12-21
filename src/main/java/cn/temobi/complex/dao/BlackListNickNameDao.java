package cn.temobi.complex.dao;

import cn.temobi.complex.entity.BlackListNickName;
import cn.temobi.core.dao.SimpleDao;

public interface BlackListNickNameDao extends SimpleDao<BlackListNickName, Long>{

	public Number checkContent(Object parameter);
}
