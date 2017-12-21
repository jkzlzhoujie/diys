package cn.temobi.complex.service;

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
import cn.temobi.complex.dao.CirclePostDiscDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dto.CirclePostDiscDto;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CirclePostAccusation;
import cn.temobi.complex.entity.CirclePostDisc;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("circlePostDiscService")
public class CirclePostDiscService extends ServiceBase{

	@Resource(name = "circlePostDiscDao")
	private CirclePostDiscDao circlePostDiscDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "circlePostAccusationDao")
	private CirclePostAccusationDao circlePostAccusationDao;
	
	@Resource(name = "circlePostDao")
	private CirclePostDao circlePostDao;
	
	@Resource(name = "blackListDao")
	private BlackListDao blackListDao;
	
	public int update(CirclePostDisc circlePostDisc){
		return circlePostDiscDao.update(circlePostDisc);
	}
	
	public Page<CirclePostDisc> findByPage(Page page,Object map){
		return circlePostDiscDao.findByPage(page, map);
	}
	
	public Page findByPageTwo(Page page, Map<String, String> map) {
		return circlePostDiscDao.findByPageTwo(page, map);
	}
	
	public List<CirclePostDisc> findAll(){
		return circlePostDiscDao.findAll();
	}
	
	public List<CirclePostDisc> findByMap(Map param){
		return circlePostDiscDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return circlePostDiscDao.getCount(map);
	}
	
	public CirclePostDisc getById(Long id){
		return circlePostDiscDao.getById(id);
	}
	
	public int save(CirclePostDisc circlePostDisc){
		return circlePostDiscDao.save(circlePostDisc);
	}
	
	public int delete(Object id){
		return circlePostDiscDao.delete(id);
	}

	public void reportCirclePostDisc(CirclePostDisc circlePostDisc,CmUserInfo reportCmUserInfo, String type) {
		
		reportCmUserInfo.setAccuCount(reportCmUserInfo.getAccuCount()+1);
		cmUserInfoDao.update(reportCmUserInfo);
		
		circlePostDisc.setIsAccusation(1);
    	circlePostDiscDao.update(circlePostDisc);
		
    	CirclePostAccusation circlePostAccusation = new CirclePostAccusation();
    	circlePostAccusation.setAccusationUserId(reportCmUserInfo.getId());
    	circlePostAccusation.setCirclePostDiscId(circlePostDisc.getId());
    	circlePostAccusation.setType(type);
    	circlePostAccusation.setUserId(circlePostDisc.getDiscUserId());
    	circlePostAccusation.setCreatedWhen(DateUtils.getCurrDateTimeStr());
    	circlePostAccusationDao.save(circlePostAccusation);
    	
	}

	/**
	 * 帖子评论
	 * @param request
	 * @param map
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public ResponseObject circlePostDiscSave(HttpServletRequest request,
			Map<String, Object> map, ResponseObject object) throws Exception {
		
		CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
		String noPic = (String)map.get("noPic");
		String circlePostId = (String)map.get("circlePostId");
		String text = (String)map.get("text");
		String emoji = (String)map.get("emoji");
		
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
    	map.put("threeMinDis", cmUserInfo.getId());
    	int numThree = circlePostDiscDao.getCount(map).intValue();
    	if(numThree >= 3){
    		object.setCode(Constant.RESPONSE_ERROR_10009);
    		object.setDesc("评论过快");
    		return object;
    	}
    	
    	if(StringUtil.isNotEmpty(text)){
    		//一天内发表了相同的评论
    		map.clear();
    		map.put("oneDayDis", cmUserInfo.getId());
    		map.put("oneText", text.trim());
    		int numDay = circlePostDiscDao.getCount(map).intValue();
    		if(numDay >= 1){
    			object.setCode(Constant.RESPONSE_ERROR_10007);
    			object.setDesc("一天内不允许发表多条相同的评论");
    			return object;
    		}
    		List<BlackListWord> list = blackListDao.findAll();
    		if(list != null && list.size() > 0)
    		{
    			for(BlackListWord blackListWord:list)
    			{
    				if(text.trim().indexOf(blackListWord.getContent()) != -1){
    					object.setCode(Constant.RESPONSE_ERROR_10010);
    					object.setDesc("你输入的内容含有敏感词");
    					return object;
    				}
    			}
    		}
    		
    	}
		
    	CirclePostDisc circlePostDisc = new  CirclePostDisc();
    	if(noPic.trim().equals("yes")){//有图片
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    		Map fileMap =multipartRequest.getFileMap();
    		//上传图片
	        if(fileMap != null && fileMap.size()>0){
	        	FileOperationController fileOperationController = new FileOperationController();
		     	List<Map<String,String>>  returnMapList = fileOperationController.uploadAppImage(request);
		       	if(returnMapList!= null && returnMapList.size()>0){
		       		for (Map<String, String> returnMap : returnMapList) {
		       			if(returnMap.get("error")!=null){
		       				object.setCode(Constant.RESPONSE_ERROR_10012);
		       				object.setDesc(returnMap.get("error"));
		       				return object;
		       			}else{
		       				circlePostDisc.setPicUrl(returnMap.get("picUrl"));
		    				circlePostDisc.setThumbnail(returnMap.get("thumbnail"));
		       			}
					}
		       	}else{
		       		object.setCode(Constant.RESPONSE_ERROR_10012);
					object.setDesc("图片上传失败");
					return object;
		       	}
	        }else{
	        	log.error("imageFile is 不能为空");
    			return object;
	        }
    	}else{// 没有图片
    		if(StringUtil.isEmpty(emoji) && StringUtil.isEmpty(text) ){
    			log.error("emoji and text  is 不能t同时为空");
    			return object;
    		}
    	}
    	
		
		circlePostDisc.setZanNum(0);
		circlePostDisc.setIsAccusation(0);
		circlePostDisc.setDiscUserId(cmUserInfo.getId());
		circlePostDisc.setDiscTime(DateUtils.getCurrDateTimeStr());
		circlePostDisc.setCirclePostId( Long.valueOf(circlePostId) ) ;
		circlePostDisc.setType(1);
		circlePostDisc.setStatus(1);
		if(StringUtil.isNotEmpty(text)){
			circlePostDisc.setText(text.trim());
		}
		if(StringUtil.isNotEmpty(emoji)){
			circlePostDisc.setEmoji(emoji.trim());
		}
		circlePostDiscDao.save(circlePostDisc);		
		
		CirclePost circlePost = circlePostDao.getById(Long.valueOf( circlePostId ));
		circlePost.setDiscussNum(circlePost.getDiscussNum()+1);
		circlePostDao.update(circlePost);
		
		CirclePostDiscDto circlePostDiscDto = new CirclePostDiscDto();
		circlePostDiscDto.setCirclePostId(circlePostDisc.getCirclePostId());
		circlePostDiscDto.setDiscHeadImageUrl(cmUserInfo.getHeadImageUrl() );
		circlePostDiscDto.setDiscNickName(cmUserInfo.getNickName());
		circlePostDiscDto.setDiscTime(circlePostDisc.getDiscTime());
		circlePostDiscDto.setDiscUserId(circlePostDisc.getDiscUserId());
		circlePostDiscDto.setId(circlePostDisc.getId());
		circlePostDiscDto.setZanNum(circlePostDisc.getZanNum());
		circlePostDiscDto.setText(circlePostDisc.getText());
		circlePostDiscDto.setEmoji(circlePostDisc.getEmoji());
		if( circlePostDisc.getPicUrl() != null ){
			circlePostDiscDto.setPicUrl(host + circlePostDisc.getPicUrl());
		}
		circlePostDiscDto.setParentCirclePostDisc(null);
		circlePostDiscDto.setCirclePost(null);
		object.setResponse(circlePostDiscDto);
		
		String content = cmUserInfo.getNickName() + "评论了您的"+ circlePost.getTitle() + "帖子，";
		PushUtil.pullOneMessage(circlePost.getUserId().toString(), content, "15", "", "");
		
		object.setResponse(circlePostDiscDto);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}

	/**
	 * 帖子回复
	 * @param request
	 * @param map
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public ResponseObject circlePostRepplySave(HttpServletRequest request,
			Map<String, Object> map, ResponseObject object) throws Exception {
		CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
		String noPic = (String)map.get("noPic");
		String circlePostId = (String)map.get("circlePostId");
		String circlePostDiscId = (String)map.get("circlePostDiscId");
		String text = (String)map.get("text");
		String emoji = (String)map.get("emoji");
		
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
    	map.put("threeMinDis", cmUserInfo.getId());
    	int numThree = circlePostDiscDao.getCount(map).intValue();
    	if(numThree >= 3){
    		object.setCode(Constant.RESPONSE_ERROR_10009);
    		object.setDesc("评论过快");
    		return object;
    	}
    	
    	if(StringUtil.isNotEmpty(text)){
    		//一天内发表了相同的评论
    		map.clear();
    		map.put("oneDayDis", cmUserInfo.getId());
    		map.put("oneText", text.trim());
    		int numDay = circlePostDiscDao.getCount(map).intValue();
    		if(numDay >= 1){
    			object.setCode(Constant.RESPONSE_ERROR_10007);
    			object.setDesc("一天内不允许发表多条相同的评论");
    			return object;
    		}
    		List<BlackListWord> list = blackListDao.findAll();
    		if(list != null && list.size() > 0)
    		{
    			for(BlackListWord blackListWord:list)
    			{
    				if(text.trim().indexOf(blackListWord.getContent()) != -1){
    					object.setCode(Constant.RESPONSE_ERROR_10010);
    					object.setDesc("你输入的内容含有敏感词");
    					return object;
    				}
    			}
    		}
    	}
		
    	CirclePostDisc circlePostDisc = new  CirclePostDisc();
    	
    	if(noPic.trim().equals("yes")){
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        Map fileMap = multipartRequest.getFileMap();
	        //上传图片
	        if(fileMap != null && fileMap.size()>0){
	        	FileOperationController fileOperationController = new FileOperationController();
	        	List<Map<String,String>>  returnMapList = fileOperationController.uploadAppImage(request);
	        	if(returnMapList!= null && returnMapList.size()>0){
	        		for (Map<String, String> returnMap : returnMapList) {
	        			if(returnMap.get("error")!=null){
	        				object.setCode(Constant.RESPONSE_ERROR_10012);
	        				object.setDesc(returnMap.get("error"));
	        				return object;
	        			}else{
	        				circlePostDisc.setPicUrl(returnMap.get("picUrl"));
	        				circlePostDisc.setThumbnail(returnMap.get("thumbnail"));
	        			}
	        		}
	        	}else{
	        		object.setCode(Constant.RESPONSE_ERROR_10012);
	        		object.setDesc("图片上传失败");
	        		return object;
	        	}
	        }else{
	        	log.error("text and imageFile is 不能同时为空");
				return object;
	        }
    	}else{// 没有图片
    		if(StringUtil.isEmpty(emoji) && StringUtil.isEmpty(text) ){
    			log.error("emoji and text  is 不能同时为空");
    			return object;
    		}
    	}
		
    	CirclePost circlePost = circlePostDao.getById(Long.valueOf( circlePostId ));
		circlePost.setDiscussNum(circlePost.getDiscussNum()+1);
		circlePostDao.update(circlePost);
    	
		circlePostDisc.setZanNum(0);
		circlePostDisc.setIsAccusation(0);
		circlePostDisc.setDiscUserId(cmUserInfo.getId());
		circlePostDisc.setDiscTime(DateUtils.getCurrDateTimeStr());
		circlePostDisc.setCirclePostId( Long.valueOf(circlePostId) ) ;
		circlePostDisc.setParentId(Long.valueOf(circlePostDiscId) ) ;
		circlePostDisc.setType(2);
		circlePostDisc.setStatus(1);
		if(StringUtil.isNotEmpty(text)){
			circlePostDisc.setText(text.trim());
		}
		if(StringUtil.isNotEmpty(emoji)){
			circlePostDisc.setEmoji(emoji.trim());
		}
		circlePostDiscDao.save(circlePostDisc);		
		
		CirclePostDiscDto circlePostDiscDto = new CirclePostDiscDto();
		circlePostDiscDto.setCirclePostId(circlePostDisc.getCirclePostId());
		circlePostDiscDto.setDiscHeadImageUrl(cmUserInfo.getHeadImageUrl());
		circlePostDiscDto.setDiscNickName(cmUserInfo.getNickName());
		circlePostDiscDto.setDiscTime(circlePostDisc.getDiscTime());
		circlePostDiscDto.setDiscUserId(circlePostDisc.getDiscUserId());
		circlePostDiscDto.setId(circlePostDisc.getId());
		circlePostDiscDto.setZanNum(circlePostDisc.getZanNum());
		circlePostDiscDto.setText(circlePostDisc.getText());
		circlePostDiscDto.setEmoji(circlePostDisc.getEmoji());
		if( circlePostDisc.getPicUrl() != null ){
			circlePostDiscDto.setPicUrl(host + circlePostDisc.getPicUrl());
		}
		//回复的评论 查找出父级评论
		CirclePostDisc parentCirclePostDisc = circlePostDiscDao.getById( Long.valueOf(circlePostDiscId) );
		if(parentCirclePostDisc != null && parentCirclePostDisc.getStatus()==1 ){
			if( parentCirclePostDisc.getPicUrl() != null ){
				parentCirclePostDisc.setPicUrl(host + parentCirclePostDisc.getPicUrl());
			}
			circlePostDiscDto.setParentCirclePostDisc(parentCirclePostDisc);
		}
		
		String content = cmUserInfo.getNickName() + "回复了您的"+ parentCirclePostDisc.getText() + "评论，";
		PushUtil.pullOneMessage(circlePost.getUserId().toString(), content, "15", "", "");
		
		circlePostDiscDto.setCirclePost(null);
		object.setResponse(circlePostDiscDto);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}

	
}
