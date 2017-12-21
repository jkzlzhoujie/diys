package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.temobi.complex.action.def.FileOperationController;
import cn.temobi.complex.dao.BlackListDao;
import cn.temobi.complex.dao.CirclePostAccusationDao;
import cn.temobi.complex.dao.CirclePostDao;
import cn.temobi.complex.dao.CirclePostPicDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CirclePostAccusation;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("circlePostService")
public class CirclePostService extends ServiceBase{

	@Resource(name = "circlePostDao")
	private CirclePostDao circlePostDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "circlePostAccusationDao")
	private CirclePostAccusationDao circlePostAccusationDao;
	
	@Resource(name = "blackListDao")
	private BlackListDao blackListDao;
	
	@Resource(name = "circlePostPicDao")
	private CirclePostPicDao circlePostPicDao;
	
	
	public int update(CirclePost circlePost){
		return circlePostDao.update(circlePost);
	}
	
	public Page<CirclePost> findByPage(Page page,Object map){
		return circlePostDao.findByPage(page, map);
	}
	
	public Page findByPageTwo(Page page, Map<String, String> map) {
		return circlePostDao.findByPageTwo(page, map);
	}
	
	public List<CirclePost> findAll(){
		return circlePostDao.findAll();
	}
	
	public List<CirclePost> findByMap(Map param){
		return circlePostDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return circlePostDao.getCount(map);
	}
	
	public CirclePost getById(Long id){
		return circlePostDao.getById(id);
	}
	
	public int save(CirclePost circlePost){
		return circlePostDao.save(circlePost);
	}
	
	public int delete(Object id){
		return circlePostDao.delete(id);
	}

	public void reportCirclePost(CirclePost circlePost,CmUserInfo reportCmUserInfo, String type) {

		reportCmUserInfo.setAccuCount(reportCmUserInfo.getAccuCount()+1);
		cmUserInfoDao.update(reportCmUserInfo);
		
		circlePost.setIsAccusation(1);
    	circlePostDao.update(circlePost);
		
    	CirclePostAccusation circlePostAccusation = new CirclePostAccusation();
    	circlePostAccusation.setAccusationUserId(reportCmUserInfo.getId());
    	circlePostAccusation.setCirclePostId(circlePost.getId());
    	circlePostAccusation.setType(type);
    	circlePostAccusation.setUserId(circlePost.getUserId());
    	circlePostAccusationDao.save(circlePostAccusation);
		
	}

	public ResponseObject releaseSave(HttpServletRequest request,Map<String,Object> map,ResponseObject object ) throws Exception {
		
		CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
		String text = (String)map.get("text");
		String circleId = (String)map.get("circleId");
		String title = (String)map.get("title");
		String noPic = (String)map.get("noPic");
		
		if(cmUserInfo.getIsAccusation() == 1){
    		object.setCode(Constant.RESPONSE_ERROR_10006);
			object.setDesc("被举报不能发帖");
			return object;
    	}
    	if(cmUserInfo.getIsBan() == 1){
    		object.setCode(Constant.RESPONSE_ERROR_10006);
			object.setDesc("禁止发帖");
			return object;
    	}
    	//三分钟内 只能发布三条
    	map.clear();
    	map.put("threeMin", cmUserInfo.getId());
    	int numThree = circlePostDao.getCount(map).intValue();
    	if(numThree >= 3){
    		object.setCode(Constant.RESPONSE_ERROR_10009);
    		object.setDesc("发帖过于集中，稍后再发");
    		return object;
    	}
    	//一天最多只能发布20个帖子
    	map.clear();
    	map.put("oneDay", cmUserInfo.getId());
    	int numOneTwenty = circlePostDao.getCount(map).intValue();
    	if(numOneTwenty >= 20){
    		object.setCode(Constant.RESPONSE_ERROR_10007);
    		object.setDesc("一天内只允许发20个帖子");
    		return object;
    	}
    	
    	//一天内发表了相同的帖子
    	map.clear();
    	map.put("oneDay", cmUserInfo.getId());
    	map.put("text", text.trim());
    	int numDay = circlePostDao.getCount(map).intValue();
    	if(numDay >= 1){
    		object.setCode(Constant.RESPONSE_ERROR_10007);
    		object.setDesc("一天内不允许发表多条相同的帖子");
    		return object;
    	}
    	
    	List<BlackListWord> list = blackListDao.findAll();
    	if(list != null && list.size() > 0)
    	{
    		for(BlackListWord blackListWord:list)
    		{
    			if(text.trim().indexOf(blackListWord.getContent()) != -1){
    				object.setCode(Constant.RESPONSE_ERROR_10010);
    				object.setDesc("你发布的内容含有敏感词");
    				return object;
    			}
    		}
    	}
    	
    	CirclePost circlePost = new  CirclePost();
		circlePost.setDiscussNum(0);
		circlePost.setShareNum(0);
		circlePost.setZanNum(0);
		circlePost.setStatus(1);
		circlePost.setIsAccusation(0);
		circlePost.setUserId(cmUserInfo.getId());
		circlePost.setCreatedWhen(DateUtils.getCurrDateTimeStr());
		circlePost.setCircleId( Long.valueOf(circleId) ) ;
		circlePost.setText(text.trim());
		circlePost.setTitle(title);
		circlePostDao.save(circlePost);		
		
		if(noPic.trim().equals("yes")){
			//上传图片
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        Map fileMap =multipartRequest.getFileMap();
	        if(fileMap != null && fileMap.size()>0){
	        	List<CirclePostPic> circlePostPicList = new ArrayList<CirclePostPic>();
	        	FileOperationController fileOperationController = new FileOperationController();
	        	List<Map<String,String>>  returnMapList = fileOperationController.uploadAppImage(request);
	        	if(returnMapList!= null && returnMapList.size()>0){
	        		for (Map<String, String> returnMap : returnMapList) {
	        			if(returnMap.get("error")!=null){
	        				object.setCode(Constant.RESPONSE_ERROR_10012);
	        				object.setDesc(returnMap.get("error"));
	        				return object;
	        			}else{
	        				CirclePostPic circlePostPic = new CirclePostPic();
	        				circlePostPic.setCirclePostId(circlePost.getId());
	        				circlePostPic.setPicUrl(returnMap.get("picUrl"));
	        				circlePostPic.setThumbnail(returnMap.get("thumbnail"));
	        				circlePostPicList.add(circlePostPic);
	        			}
	        		}
	        		circlePostPicDao.saveBatch(circlePostPicList);
	        	}else{
	        		object.setCode(Constant.RESPONSE_ERROR_10012);
	        		object.setDesc("图片上传失败");
	        		return object;
	        	}
	        }
		}
		
		object.setResponse(circlePost);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
		
	}

	
	
	
	
}
