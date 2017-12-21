package cn.temobi.complex.dao;

import java.util.List;

import cn.temobi.complex.entity.PmProductCollectPic;
import cn.temobi.core.dao.SimpleDao;

public interface PmProductCollectPicDao extends SimpleDao<PmProductCollectPic, Long>{

	void saveBatch(List<PmProductCollectPic> pmProductCollectPicList);

}
