package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmProductDiscussDao;
import cn.temobi.complex.dto.MyDiscussDto;
import cn.temobi.complex.dto.PmProductDiscussDto;
import cn.temobi.complex.dto.TwoDiscussDto;
import cn.temobi.complex.entity.PmProductDiscuss;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmProductDiscussDao")
public class PmProductDiscussDaoImpl extends SimpleMybatisSupport<PmProductDiscuss, Long> implements PmProductDiscussDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmProductDiscuss";
	}

	@Override
	public List<PmProductDiscussDto> findDto(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findDto"), map);
	}

	@Override
	public List<TwoDiscussDto> findDtoTo(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findDtoTo"), map);
	}

	@Override
	public List<MyDiscussDto> findMyDiscuss(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findMyDiscuss"), map);
	}
	
	
	@Override
	public Number findDiscussCount(Map<String, Object> map) {
		 return (Number) this.getSqlSession().selectOne(toMybatisStatement("findDiscussCount"), map);
	}

	@Override
	public List<Long> findIdList(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findIdList"), map);
	}
	
	
	
	

}
