package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysHotLabelsDao;
import cn.temobi.complex.entity.SysHotLabels;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysHotLabelsDao")
public class SysHotLabelsDaoImpl extends SimpleMybatisSupport<SysHotLabels, Long> implements SysHotLabelsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysHotLabels";
	}

}
