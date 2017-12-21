package cn.temobi.complex.dao;

import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.core.dao.SimpleDao;

public interface BlackListDao extends SimpleDao<BlackListWord, Long>{

	public Number checkContent(Object parameter);
}
