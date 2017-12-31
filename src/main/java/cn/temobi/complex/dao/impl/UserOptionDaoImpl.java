package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.UserOptionDao;
import cn.temobi.complex.entity.BackgroundPic;
import cn.temobi.complex.entity.NetRedUser;
import cn.temobi.complex.entity.NetRedUserLabImg;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.SimpleMybatisSupport;
import cn.temobi.core.dao.SimpleDao;
@Component
@Repository("userOptionDao")
public class UserOptionDaoImpl extends SimpleMybatisSupport<NetRedUser, Long> implements UserOptionDao{
    @Override
    public String getMybatisMapperNamesapce() {
            return "cn.temobi.complex.entity.NetRedUser";
    }

    @Override
    public List<VoteRecord> supperCount() {
        // TODO Auto-generated method stub
        return getSqlSession().selectList(toMybatisStatement("supperCount"));
    }

    @Override
    public void insertLable(List<NetRedUserLabImg> list) {
        // TODO Auto-generated method stub
        this.getSqlSession().insert(toMybatisStatement("insertLable"), list);
    }

    @Override
    public void insertImage(List<NetRedUserLabImg> list) {
        // TODO Auto-generated method stub
        this.getSqlSession().insert(toMybatisStatement("insertImage"), list);
    }

    @Override
    public void deleteImage(Map<String, Long> map) {
        // TODO Auto-generated method stub
        this.getSqlSession().delete(toMybatisStatement("deleteImage"), map);
    }

    @Override
    public void deleteLable(Map<String, Long> map) {
        // TODO Auto-generated method stub
        this.getSqlSession().delete(toMybatisStatement("deleteLable"), map);
    }

    @Override
    public List<NetRedUserLabImg> selImages(Map<String, Long> map) {
        // TODO Auto-generated method stub
        return this.getSqlSession().selectList(toMybatisStatement("selImages"), map);
    }

    @Override
    public List<NetRedUserLabImg> selLables(Map<String, Long> map) {
        return this.getSqlSession().selectList(toMybatisStatement("selLables"), map);
    }
}
