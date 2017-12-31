package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.WeixinAccessRecordDao;
import cn.temobi.complex.entity.AccessRecord;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("weixinAccessRecordDao")
public class WeixinAccessRecordDaoImpl extends SimpleMybatisSupport<AccessRecord, Long> implements WeixinAccessRecordDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccessRecord";
	}

	
}
