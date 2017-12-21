package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmProductAccusationDao;
import cn.temobi.complex.dto.PmProductAccusationDto;
import cn.temobi.complex.entity.PmProductAccusation;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmProductAccusationDao")
public class PmProductAccusationDaoImpl extends SimpleMybatisSupport<PmProductAccusation, Long> implements PmProductAccusationDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmProductAccusation";
	}

	@Override
	public Page<PmProductAccusationDto> findDtoByPage(
			Page<PmProductAccusationDto> page, Object parameter) {
		Number totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("countDto"), toParameterMap(parameter));
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findDtoByPage"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}

}
