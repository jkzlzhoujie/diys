package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.BlackListDao;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("designerOrderService")
public class DesignerOrderService extends ServiceBase{

	@Resource(name = "blackListDao")
	private BlackListDao blackListDao;
	
}
