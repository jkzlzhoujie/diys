package cn.temobi.complex.dao;

import cn.temobi.complex.dto.PmProductAccusationDto;
import cn.temobi.complex.entity.PmProductAccusation;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;


public interface PmProductAccusationDao extends SimpleDao<PmProductAccusation, Long> {

	public Page<PmProductAccusationDto> findDtoByPage(Page<PmProductAccusationDto> page, Object parameter);
}
