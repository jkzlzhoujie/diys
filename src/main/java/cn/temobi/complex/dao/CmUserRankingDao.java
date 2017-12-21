package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserRanking;
import cn.temobi.core.dao.SimpleDao;

public interface CmUserRankingDao extends SimpleDao<CmUserRanking, Long>{

	public void executeSql(List<CmUserInfo> list);
	
	public void executeSqlStrat();
	
	public void executeSqlEnd();
	
	public Number maxNum(Map<String,Object> map);
}
