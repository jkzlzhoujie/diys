package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.Feedback;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 26 09:38:40
 */
public interface FeedbackDao extends SimpleDao<Feedback, Long> {

	public Number findFeedbackCount( Map<String, Object> map);
	
}
