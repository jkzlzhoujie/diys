package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dto.AccountUserBoDto;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("accountUserDao")
public class AccountUserDaoImpl extends SimpleMybatisSupport<AccountUser, Long> implements AccountUserDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccountUser";
	}

	@Override
	public Page<AccountUserBoDto> findByPageBo(Page<AccountUserBoDto> page,Object parameter) {
		Number totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("countBo"), toParameterMap(parameter));
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findByPageBo"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}
}
