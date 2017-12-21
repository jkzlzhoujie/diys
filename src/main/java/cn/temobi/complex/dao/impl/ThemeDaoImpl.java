package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.ThemeDao;
import cn.temobi.complex.entity.ClientVersion;
import cn.temobi.complex.entity.Theme;
import cn.temobi.complex.entity.ThemeUseCount;
import cn.temobi.core.common.SimpleMybatisSupport;
import cn.temobi.core.util.MybatisStatementUtils;

@Component
@Repository("themeDao")
public class ThemeDaoImpl extends SimpleMybatisSupport<Theme, Long> implements ThemeDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Theme";
	}

	public Number sumLove(Map<String,String> map) {
        return (Number) getSqlSession().selectOne(toMybatisStatement("sumLove"),map);
    }
	
}
