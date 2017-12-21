package cn.temobi.complex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmDesignerSaleCommentDao;
import cn.temobi.complex.entity.CmDesignerSaleComment;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("cmDesignerSaleCommentDao")
public class CmDesignerSaleCommentDaoImpl extends SimpleMybatisSupport<CmDesignerSaleComment, Long> implements CmDesignerSaleCommentDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmDesignerSaleComment";
	}

	@Override
	public void saveBatch(List<CmDesignerSaleComment> circlePostPicList) {
		getSqlSession().insert(toMybatisStatement("insertBatch"), circlePostPicList);
		
	}


	
}
