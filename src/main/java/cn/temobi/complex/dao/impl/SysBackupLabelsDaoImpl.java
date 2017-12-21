package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysBackupLabelsDao;
import cn.temobi.complex.entity.SysBackupLabels;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysBackupLabelsDao")
public class SysBackupLabelsDaoImpl extends SimpleMybatisSupport<SysBackupLabels, Long> implements SysBackupLabelsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysBackupLabels";
	}

}
