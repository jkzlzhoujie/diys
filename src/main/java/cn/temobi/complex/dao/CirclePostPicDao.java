package cn.temobi.complex.dao;

import java.util.List;

import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.core.dao.SimpleDao;

public interface CirclePostPicDao extends SimpleDao<CirclePostPic, Long>{

	void saveBatch(List<CirclePostPic> circlePostPicList);

}
