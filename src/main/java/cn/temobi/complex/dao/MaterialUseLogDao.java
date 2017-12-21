package cn.temobi.complex.dao;

import java.util.List;

import cn.temobi.complex.entity.MaterialUseLog;
import cn.temobi.core.dao.SimpleDao;

public interface MaterialUseLogDao extends SimpleDao<MaterialUseLog, Long> {

	public void insertList(List<MaterialUseLog> list);
}
