package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.UserOptionDao;
import cn.temobi.complex.dao.WeixinAccessRecordDao;
import cn.temobi.complex.entity.NetRedUser;
import cn.temobi.complex.entity.NetRedUserLabImg;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.PropertiesHelper;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("userOptionService")
public class UserOptionService extends ServiceBase{
	
	String host = PropertiesHelper.getProperty("properties/server_information.properties", "host_url");
	
	@Resource(name = "userOptionDao")
	private UserOptionDao dao;
	@Resource(name = "weixinAccessRecordDao")
	private WeixinAccessRecordDao recordDao;
	
	
	public int update(NetRedUser entity){
		if(entity.getImagesArr() != null && entity.getImagesArr().length > 0)
			saveImage(Arrays.asList( entity.getImagesArr() ),entity.getId());
		if(entity.getLablesArr()!=null && entity.getLablesArr().length>0)
            saveLables(Arrays.asList( entity.getLablesArr()),entity.getId());
            return dao.update(entity);
	}
	
	public Page<NetRedUser> findByPage(Page page,Object map){
		return dao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return dao.getCount(map);
	}
	
	public NetRedUser getById(Long id){
	    NetRedUser user = dao.getById(id);
	    Map<String,Long> map = new HashMap<String,Long>();
	    map.put("userId", id);
	    List<NetRedUserLabImg> imags = dao.selImages(map);
	    List<NetRedUserLabImg> lables = dao.selLables(map);
	    if(imags == null || imags.size()<=0) imags = new ArrayList<NetRedUserLabImg>();
	    if(lables == null || lables.size()<=0) lables = new ArrayList<NetRedUserLabImg>();
	    List<String> imgList = new ArrayList<String>();
	    List<String> labList = new ArrayList<String>();
	    for(NetRedUserLabImg img:imags){
	        imgList.add(img.getImage());
	    }
	    for(NetRedUserLabImg lab : lables){
	        labList.add( lab.getLableName()); 
	    }
	    user.setImagesArr( (String[])imgList.toArray(new String[imgList.size()]) );
	    user.setLablesArr(  (String[])labList.toArray(new String[labList.size()]) );
	    return user;
	}
	
	public int save(NetRedUser entity){
	    return dao.save(entity);
	}
	
	public void saveImage(List<String> images,long userId){
	    List<NetRedUserLabImg> list = new ArrayList<NetRedUserLabImg>();
	    for(String image :images){
	        NetRedUserLabImg nrs = new NetRedUserLabImg();
	        nrs.setUserId(userId);
	        nrs.setImage(image);
	        list.add(nrs);
	    }
	    deleteImages(userId);
	    dao.insertImage(list);
	    
	}
	public void saveLables(List<String> lables,long userId){
	    List<NetRedUserLabImg> list = new ArrayList<NetRedUserLabImg>();
            for(String lable :lables){
                NetRedUserLabImg nrs = new NetRedUserLabImg();
                nrs.setUserId(userId);
                nrs.setLableName(lable);
                list.add(nrs);
            }
            deleteLables(userId);
	    dao.insertLable(list);
	}
	
	public void deleteLables(long userId){
	    Map<String,Long> map = new HashMap<String,Long>();
	    map.put("userId", userId);
            dao.deleteLable(map);
        }
    
	public void deleteImages(long userId){
	    Map<String,Long> map = new HashMap<String,Long>();
            map.put("userId", userId);
            dao.deleteImage(map);
        }
    
	public int delete(Object id){
		return dao.delete(id);
	}
	
	public int delete(NetRedUser entity){
		return dao.delete(entity);
	}
	
	public List findByMap(Map map){
		return dao.findByMap(map);
	}
	
	public int supperCount(){
	    List<VoteRecord> list = dao.supperCount();
	    if(list == null || list.size()<=0){
	        list = new ArrayList<VoteRecord>();
	    }
	    int i = 0;
	    for(VoteRecord vo : list){
	        int count = vo.getCount();
	        int callCount = vo.getCallCount();
	        i += count + callCount;
	    }
	    return i;
	}
	public int accessRecordCount(){
	    return recordDao.getCount(new Object()).intValue();
	}
	
}
