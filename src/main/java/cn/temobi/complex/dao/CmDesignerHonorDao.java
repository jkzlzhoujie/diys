package cn.temobi.complex.dao;

import java.util.List;

import cn.temobi.complex.entity.CmDesignerHonor;
import cn.temobi.complex.entity.CmDesignerInfoPic;
import cn.temobi.core.dao.SimpleDao;

public interface CmDesignerHonorDao extends SimpleDao<CmDesignerHonor, Long>{

	void saveBatch(List<CmDesignerHonor> cmDesignerHonor);

	
}
