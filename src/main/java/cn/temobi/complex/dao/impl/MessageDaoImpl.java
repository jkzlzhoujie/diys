package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.MessageDao;
import cn.temobi.complex.entity.Message;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("messageDao")
public class MessageDaoImpl extends SimpleMybatisSupport<Message, Long> implements MessageDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Message";
	}
    
}
