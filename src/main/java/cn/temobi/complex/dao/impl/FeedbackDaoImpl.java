package cn.temobi.complex.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.FeedbackDao;
import cn.temobi.complex.entity.Feedback;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("feedbackDao")
public class FeedbackDaoImpl extends SimpleMybatisSupport<Feedback, Long> implements FeedbackDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Feedback";
	}

	@Override
	public Number findFeedbackCount(Map<String, Object> map) {
		 return (Number) this.getSqlSession().selectOne(toMybatisStatement("findFeedbackCount"), map);
	}
	
}
