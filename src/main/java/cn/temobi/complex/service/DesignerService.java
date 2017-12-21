package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.temobi.complex.action.def.FileOperationController;
import cn.temobi.complex.dao.BlackListDao;
import cn.temobi.complex.dao.CmDesignerDao;
import cn.temobi.complex.dao.CmDesignerProductFormatDao;
import cn.temobi.complex.dao.CmDesignerProductInfoDao;
import cn.temobi.complex.entity.CmDesignerInfo;
import cn.temobi.complex.entity.CmDesignerProductFormat;
import cn.temobi.complex.entity.CmDesignerProductInfo;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.JsonUtils;
import cn.temobi.core.util.StringUtil;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("designerService")
public class DesignerService extends ServiceBase{

	@Resource(name = "cmDesignerDao")
	private CmDesignerDao cmDesignerDao;
	
	@Resource(name = "cmDesignerProductInfoDao")
	private CmDesignerProductInfoDao cmDesignerProductInfoDao;

	@Resource(name = "cmDesignerProductFormatDao")
	private CmDesignerProductFormatDao cmDesignerProductFormatDao;

	static void uploadImage(HttpServletRequest request ,List<Map<String,String>>  returnMapList ,ResponseObject object){
		try {
			//上传图片
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        Map fileMap =multipartRequest.getFileMap();
	        if(fileMap != null && fileMap.size()>0){
	        	FileOperationController fileOperationController = new FileOperationController();
	        	returnMapList = fileOperationController.uploadDesignerImage(request);
	        	if(returnMapList!= null && returnMapList.size()>0){
	        		for (Map<String, String> returnMap : returnMapList) {
	        			if(returnMap.get("error")!=null){
	        				object.setCode(Constant.RESPONSE_ERROR_10012);
	        				object.setDesc("图片上传失败");
	        			}
	        		}
	        	}else{
	        		object.setCode(Constant.RESPONSE_ERROR_10012);
	        		object.setDesc("图片上传失败");
	        	}
	        }else{
	        	object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
        		object.setDesc("请选择图片");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加商品
	 * @param object
	 * @param request
	 * @return
	 */
	public ResponseObject addDesignerGoods(ResponseObject object,HttpServletRequest request) {
		String designerId = request.getParameter("designerId");
		String name = request.getParameter("name");
		String isVirtual = request.getParameter("isVirtual");
		String productDescribe = request.getParameter("productDescribe");
		String picFlag = request.getParameter("picFlag");
		if(StringUtil.isEmpty(designerId) || StringUtil.isEmpty(name) || StringUtil.isEmpty(productDescribe) 
				|| StringUtil.isEmpty(isVirtual) || StringUtil.isEmpty(picFlag) ){
			 return object;
		}
		CmDesignerInfo cmDesignerInfo = cmDesignerDao.getById(Long.valueOf(designerId));
		if(cmDesignerInfo==null){
			return object;
		}
		CmDesignerProductInfo  cmDesignerProductInfo = new CmDesignerProductInfo();
		cmDesignerProductInfo.setDesignerId(cmDesignerInfo.getId());
		cmDesignerProductInfo.setIsVirtual( Integer.valueOf(isVirtual) );
		cmDesignerProductInfo.setStatus(1);
		cmDesignerProductInfo.setProductDescribe(productDescribe);
		cmDesignerProductInfo.setName(name);
		
		if(picFlag.trim().equals("yes")){
			List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
			uploadImage(request, returnMapList, object);
			if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
				return object;
			}
			if( returnMapList != null && returnMapList.size() > 0){
				cmDesignerProductInfo.setProductImgUrl(returnMapList.get(0).get("url"));
				cmDesignerProductInfo.setProductImgUrlThumbnail(returnMapList.get(0).get("thumbnail"));
			}
		}
		//---  规格 用 json 格式接收
		List<CmDesignerProductFormat> cmDesignerProductFormatList = new ArrayList<CmDesignerProductFormat>();
		String jsonString = request.getParameter("productFormat");
		if(StringUtil.isNotEmpty(jsonString)){
			JSONObject json = JsonUtils.toJSONObject(jsonString);
			JSONArray jsonArray = json.getJSONArray("cmDesignerProductFormat");  
		    for(int i=0; i<jsonArray.size(); i++){  
		        JSONObject productFormat =(JSONObject) jsonArray.get(i);  
		        String format = (String) productFormat.get("format");
		        String price  =  (String) productFormat.get("price"); 
		        String productNum = (String) productFormat.get("productNum"); 
		        CmDesignerProductFormat cmDesignerProductFormat = new CmDesignerProductFormat();
		        cmDesignerProductFormat.setFormat(format);
		        cmDesignerProductFormat.setPrice(Double.valueOf(price));
		        cmDesignerProductFormat.setProductNum(Integer.valueOf(productNum));
		        cmDesignerProductFormatList.add(cmDesignerProductFormat);
		    }  
		}else{
			return object;
		}
		cmDesignerProductFormatDao.saveBatch(cmDesignerProductFormatList);
		cmDesignerProductInfoDao.save(cmDesignerProductInfo);
		object.setResponse(cmDesignerProductInfo);
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		object.setDesc("成功");
		return object;
	}
	
	/**
	 * 修改商品
	 * @param object
	 * @param request
	 * @return
	 */
	public ResponseObject updateDesignerGoods(ResponseObject object,
			HttpServletRequest request) {
		String id = request.getParameter("id");
		String picFlag = request.getParameter("picFlag");
		if(StringUtil.isEmpty(id) || StringUtil.isEmpty(picFlag)){
			 return object;
		}
		CmDesignerProductInfo cmDesignerProductInfo = cmDesignerProductInfoDao.getById(Long.valueOf(id));
		String name = request.getParameter("name");
		if(StringUtil.isNotEmpty(name)){
			cmDesignerProductInfo.setName(name);
		}
		String productDescribe = request.getParameter("productDescribe");
		if(StringUtil.isNotEmpty(productDescribe)){
			cmDesignerProductInfo.setProductDescribe(productDescribe);
		}
		String isVirtual = request.getParameter("isVirtual");
		if(StringUtil.isNotEmpty(isVirtual)){
			cmDesignerProductInfo.setIsVirtual( Integer.valueOf(isVirtual) );
		}
		if(picFlag.trim().equals("yes")){
			List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
			uploadImage(request, returnMapList, object);
			if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
				return object;
			}
			if( returnMapList != null && returnMapList.size() > 0){
				cmDesignerProductInfo.setProductImgUrl(returnMapList.get(0).get("url"));
				cmDesignerProductInfo.setProductImgUrlThumbnail(returnMapList.get(0).get("thumbnail"));
			}
		}
		//---  规格 用 json 格式接收
		List<CmDesignerProductFormat> cmDesignerProductFormatList = new ArrayList<CmDesignerProductFormat>();
		String jsonString = request.getParameter("productFormat");
		if(StringUtil.isNotEmpty(jsonString)){
			JSONObject json = JsonUtils.toJSONObject(jsonString);
			JSONArray jsonArray = json.getJSONArray("cmDesignerProductFormat");  
		    for(int i=0; i<jsonArray.size(); i++){  
		        JSONObject productFormat =(JSONObject) jsonArray.get(i);  
		        String format = (String) productFormat.get("format");
		        String price  =  (String) productFormat.get("price"); 
		        String productNum = (String) productFormat.get("productNum"); 
		        CmDesignerProductFormat cmDesignerProductFormat = new CmDesignerProductFormat();
		        cmDesignerProductFormat.setFormat(format);
		        cmDesignerProductFormat.setPrice(Double.valueOf(price));
		        cmDesignerProductFormat.setProductNum(Integer.valueOf(productNum));
		        cmDesignerProductFormatList.add(cmDesignerProductFormat);
		    }  
		}else{
			return object;
		}
		
		cmDesignerProductFormatDao.deleteByProductId(cmDesignerProductInfo.getId());
		cmDesignerProductFormatDao.saveBatch(cmDesignerProductFormatList);
		cmDesignerProductInfoDao.update(cmDesignerProductInfo);
		object.setResponse(cmDesignerProductInfo);
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		object.setDesc("成功");
		return object;
	}
	
}
