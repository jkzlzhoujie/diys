package cn.temobi.complex.dao;

import cn.temobi.complex.dto.AccountUserBoDto;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface AccountUserDao extends SimpleDao<AccountUser, Long> {

	public Page<AccountUserBoDto> findByPageBo(Page<AccountUserBoDto> page, Object parameter);
}
