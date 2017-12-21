package cn.temobi.complex.dao;

import java.util.List;

import cn.temobi.complex.entity.CmDesignerProductFormat;
import cn.temobi.core.dao.SimpleDao;

public interface CmDesignerProductFormatDao extends SimpleDao<CmDesignerProductFormat, Long>{

	void saveBatch(List<CmDesignerProductFormat> cmDesignerProductFormatList);

	void deleteByProductId(Long productId);
	
}
