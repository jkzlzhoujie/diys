package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.NetRedUser;
import cn.temobi.complex.entity.NetRedUserLabImg;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface UserOptionDao extends SimpleDao<NetRedUser, Long> {
    
    public List<VoteRecord> supperCount();
    
    public void insertLable(List<NetRedUserLabImg> list);
    
    public void insertImage(List<NetRedUserLabImg> list);
    
    public List<NetRedUserLabImg> selImages(Map<String,Long> map);
    
    public List<NetRedUserLabImg> selLables(Map<String,Long> map);
    
    public void deleteImage(Map<String,Long> map);
    
    public void deleteLable(Map<String,Long> map);
    
    public Page<NetRedUser> getGameIndex(Page<NetRedUser> page, Map<String, Object> parameter);
    
    
}
