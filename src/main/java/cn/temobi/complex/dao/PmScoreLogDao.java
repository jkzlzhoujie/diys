package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.CountScoreDto;
import cn.temobi.complex.entity.PmScoreLog;
import cn.temobi.core.dao.SimpleDao;

public interface PmScoreLogDao extends SimpleDao<PmScoreLog,Long>{

	public List<CountScoreDto> findCountByUser(Map<String, Object> map);
}
