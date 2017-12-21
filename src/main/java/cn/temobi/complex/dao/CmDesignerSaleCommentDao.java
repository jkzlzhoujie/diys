package cn.temobi.complex.dao;

import java.util.List;

import cn.temobi.complex.entity.CmDesignerSaleComment;
import cn.temobi.core.dao.SimpleDao;

public interface CmDesignerSaleCommentDao extends SimpleDao<CmDesignerSaleComment, Long>{

	void saveBatch(List<CmDesignerSaleComment> cmDesignerSaleCommentList);

}
