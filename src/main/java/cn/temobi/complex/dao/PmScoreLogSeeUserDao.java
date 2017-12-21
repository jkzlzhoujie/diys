package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.CountScoreDto;
import cn.temobi.complex.entity.PmScoreLogSeeUser;
import cn.temobi.core.dao.SimpleDao;

public interface PmScoreLogSeeUserDao extends SimpleDao<PmScoreLogSeeUser,Long>{

	public List<CountScoreDto> findCountByUser(Map<String, Object> map);
}
