package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.MaterialDownloadDao;
import cn.temobi.complex.entity.MaterialDownload;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("materialDownloadDao")
public class MaterialDownloadDaoImpl extends SimpleMybatisSupport<MaterialDownload, Long> implements MaterialDownloadDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.MaterialDownload";
	}

}
