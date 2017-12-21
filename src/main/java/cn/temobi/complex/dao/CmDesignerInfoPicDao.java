package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.CmDesignerInfoPic;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface CmDesignerInfoPicDao extends SimpleDao<CmDesignerInfoPic, Long>{

	void saveBatch(List<CmDesignerInfoPic> cmDesignerInfoPicList);

}
