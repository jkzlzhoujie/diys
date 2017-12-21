package cn.temobi.complex.action.def;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.temobi.complex.schedule.CircleJob;
import cn.temobi.core.common.Constant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.ImgCompressUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;


/**
 * @author salim
 * @created 2012-6-4
 * @package com.plaminfo.imusic.action
 */
@Controller
public class FileOperationController {
	private static Logger log = LoggerFactory.getLogger(FileOperationController.class);
	
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
    String home_path_prefix = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");  
    String resource_path = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
	
	/**
	 * 压缩包上传
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload/zip", method = RequestMethod.POST)
	public String uploadZip(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
//        String savePath = request.getSession().getServletContext().getRealPath("/")+ "upload" + java.io.File.separator+ "images"+ java.io.File.separator+ DateUtils.getCurrentDateTime("yyyyMM") + java.io.File.separator+"avatars"+java.io.File.separator; // 图片保存路径
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String saveUrl = homePath + resourcePath + "upload/";
		String savePath = resourcePath + "upload/";
        final long MAX_SIZE = 1024*1024*10;  
        // 允许上传的文件格式的列表 final  
        response.setContentType("text/html"); // 设置字符编码为UTF-8, 这样支持汉字显示  
        response.setCharacterEncoding("UTF-8");  
        // 实例化一个硬盘文件工厂,用来配置上传组件  
        DiskFileItemFactory dfif = new DiskFileItemFactory();  
        // 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
        dfif.setSizeThreshold(4096); // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
        File tmpFile = new File(saveUrl);  
        if (!tmpFile.exists()) {  
            tmpFile.mkdirs();  
        }  
        dfif.setRepository(tmpFile);  
  
        PrintWriter out = response.getWriter();  
        
        MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
        Map map=re.getFileMap();

        //key 是input框中的 name 属性 value 得到的MultipartFile对象
        List fileList =new ArrayList();

        for( Object obj :map.values()){
         MultipartFile file=(MultipartFile)obj;
         fileList.add(file);
        }
  
        // 没有文件上传  
        if (fileList == null || fileList.size() == 0) {  
            out.println("please upload file first <p/>");  
            return null;  
        }  
  
        // 得到所有上传的文件  
        Iterator fileItr = fileList.iterator(); // 循环处理所有文件  
        while (fileItr.hasNext()) {  
        	MultipartFile fileItem = null;  
            String path = null;  
            long size = 0;  
            // 得到当前文件  
            fileItem = (MultipartFile) fileItr.next();  
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
            if (fileItem == null || fileItem.isEmpty()) {  
                continue; // 得到文件的完整路径  
            }  
            path = fileItem.getOriginalFilename();  
            // 得到文件的大小  
            size = fileItem.getSize();  
            if ("".equals(path) || size == 0 || size > MAX_SIZE) {  
                out.println("<script type=\"text/javascript\">alert('catch error during file upload as file is too large,please try again!');</script>"); // return; 
                return null;
            }  
            // 得到去除路径的文件名  
            String t_name = path.substring(path.lastIndexOf("\\") + 1);  
            // 得到文件的扩展名(无扩展名时将得到全名)  
            String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
            // 拒绝接受规定文件格式之外的文件类型  
//            if (t_ext!=null && allowedExt.indexOf(t_ext.toLowerCase())==-1) {  
//                out.println("please upload allowed file with correct type <p/>");  
//                return null;
//            }  
            try { // 保存文件  
            	String datestr =DateUtils.getCurrentDateTime("yyyyMMddHHmmss")+RandomUtils.getRandomStr(6);
                fileItem.transferTo(new File(saveUrl+"t_"+datestr+"."+t_ext)); 
                out.print(savePath+"t_"+datestr+"."+t_ext); 
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return null;
    }
	
	@RequestMapping(value = "/upload/app", method = RequestMethod.POST)
	public String uploadApk(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
//        String savePath = request.getSession().getServletContext().getRealPath("/")+ "upload" + java.io.File.separator+ "images"+ java.io.File.separator+ DateUtils.getCurrentDateTime("yyyyMM") + java.io.File.separator+"avatars"+java.io.File.separator; // 图片保存路径
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String saveUrl = homePath + resourcePath + "app/";
		String savePath = resourcePath + "app/";
		final long MAX_SIZE = 1024*1024*100;  
		// 允许上传的文件格式的列表 final  
		String allowedExt = new String("*.zip;*.apk");  
		response.setContentType("text/html"); // 设置字符编码为UTF-8, 这样支持汉字显示  
		response.setCharacterEncoding("UTF-8");  
		// 实例化一个硬盘文件工厂,用来配置上传组件  
		DiskFileItemFactory dfif = new DiskFileItemFactory();  
		// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
		dfif.setSizeThreshold(4096); // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
		File tmpFile = new File(saveUrl);  
		if (!tmpFile.exists()) {  
			tmpFile.mkdirs();  
		}  
		dfif.setRepository(tmpFile);  
		
		PrintWriter out = response.getWriter();  
		
		MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
		Map map=re.getFileMap();
		
		//key 是input框中的 name 属性 value 得到的MultipartFile对象
		List fileList =new ArrayList();
		
		for( Object obj :map.values()){
			MultipartFile file=(MultipartFile)obj;
			fileList.add(file);
		}
		
		// 没有文件上传  
		if (fileList == null || fileList.size() == 0) {  
			out.println("please upload file first <p/>");  
			return null;  
		}  
		
		// 得到所有上传的文件  
		Iterator fileItr = fileList.iterator(); // 循环处理所有文件  
		while (fileItr.hasNext()) {  
			MultipartFile fileItem = null;  
			String path = null;  
			long size = 0;  
			// 得到当前文件  
			fileItem = (MultipartFile) fileItr.next();  
			// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
			if (fileItem == null || fileItem.isEmpty()) {  
				continue; // 得到文件的完整路径  
			}  
			path = fileItem.getOriginalFilename();  
			// 得到文件的大小  
			size = fileItem.getSize();  
			if ("".equals(path) || size == 0 || size > MAX_SIZE) {  
				out.println("<script type=\"text/javascript\">alert('catch error during file upload as file is too large,please try again!');</script>"); // return; 
				return null;
			}  
			// 得到去除路径的文件名  
			String t_name = path.substring(path.lastIndexOf("\\") + 1);  
			// 得到文件的扩展名(无扩展名时将得到全名)  
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
			// 拒绝接受规定文件格式之外的文件类型  
			if (t_ext!=null && allowedExt.indexOf(t_ext.toLowerCase())==-1) {  
				out.println("please upload allowed file with correct type <p/>");  
				return null;
			}  
			try { // 保存文件  
				String datestr =DateUtils.getCurrentDateTime("yyyyMMddHHmmss")+RandomUtils.getRandomStr(6);
				fileItem.transferTo(new File(saveUrl+"r_"+datestr+"."+t_ext)); 
				out.print(savePath+"r_"+datestr+"."+t_ext); 
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		return null;
	}
	
	@RequestMapping(value = "/upload/userApp", method = RequestMethod.POST)
	public String userApp(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
//        String savePath = request.getSession().getServletContext().getRealPath("/")+ "upload" + java.io.File.separator+ "images"+ java.io.File.separator+ DateUtils.getCurrentDateTime("yyyyMM") + java.io.File.separator+"avatars"+java.io.File.separator; // 图片保存路径
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String saveUrl = homePath + resourcePath + "app/userApp/";
		String savePath = resourcePath + "app/userApp/";
		final long MAX_SIZE = 1024*1024*100;  
		// 允许上传的文件格式的列表 final  
		String allowedExt = new String("*.zip;*.apk");  
		response.setContentType("text/html"); // 设置字符编码为UTF-8, 这样支持汉字显示  
		response.setCharacterEncoding("UTF-8");  
		// 实例化一个硬盘文件工厂,用来配置上传组件  
		DiskFileItemFactory dfif = new DiskFileItemFactory();  
		// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
		dfif.setSizeThreshold(4096); // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
		File tmpFile = new File(saveUrl);  
		if (!tmpFile.exists()) {  
			tmpFile.mkdirs();  
		}  
		dfif.setRepository(tmpFile);  
		
		PrintWriter out = response.getWriter();  
		
		MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
		Map map=re.getFileMap();
		
		//key 是input框中的 name 属性 value 得到的MultipartFile对象
		List fileList =new ArrayList();
		
		for( Object obj :map.values()){
			MultipartFile file=(MultipartFile)obj;
			fileList.add(file);
		}
		
		// 没有文件上传  
		if (fileList == null || fileList.size() == 0) {  
			out.println("please upload file first <p/>");  
			return null;  
		}  
		
		// 得到所有上传的文件  
		Iterator fileItr = fileList.iterator(); // 循环处理所有文件  
		while (fileItr.hasNext()) {  
			MultipartFile fileItem = null;  
			String path = null;  
			long size = 0;  
			// 得到当前文件  
			fileItem = (MultipartFile) fileItr.next();  
			// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
			if (fileItem == null || fileItem.isEmpty()) {  
				continue; // 得到文件的完整路径  
			}  
			path = fileItem.getOriginalFilename();  
			// 得到文件的大小  
			size = fileItem.getSize();  
			if ("".equals(path) || size == 0 || size > MAX_SIZE) {  
				out.println("<script type=\"text/javascript\">alert('catch error during file upload as file is too large,please try again!');</script>"); // return; 
				return null;
			}  
			// 得到去除路径的文件名  
			String t_name = path.substring(path.lastIndexOf("\\") + 1);  
			// 得到文件的扩展名(无扩展名时将得到全名)  
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
			// 拒绝接受规定文件格式之外的文件类型  
			if (t_ext!=null && allowedExt.indexOf(t_ext.toLowerCase())==-1) {  
				out.println("please upload allowed file with correct type <p/>");  
				return null;
			}  
			try { // 保存文件  
				fileItem.transferTo(new File(saveUrl+t_name)); 
				out.print(host+savePath+t_name); 
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		return null;
	}
	
	/**
	 * banner图上传
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload/banner", method = RequestMethod.POST)
	public String uploadBanner(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		log.error("开始 上传 banner图片");
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String saveUrl = homePath + resourcePath + "banner/";
		String savePath = resourcePath + "banner/";
        final long MAX_SIZE = 10*1024*1024;  
        // 允许上传的文件格式的列表 final  
        String allowedExt = new String("*.png;*.jpg");  
        response.setContentType("text/html"); // 设置字符编码为UTF-8, 这样支持汉字显示  
        response.setCharacterEncoding("UTF-8");  
        // 实例化一个硬盘文件工厂,用来配置上传组件  
        DiskFileItemFactory dfif = new DiskFileItemFactory();  
        // 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
        dfif.setSizeThreshold(4096); // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
        File tmpFile = new File(saveUrl);  
        if (!tmpFile.exists()) {  
            tmpFile.mkdirs();  
        }  
        dfif.setRepository(tmpFile);  
  
        PrintWriter out = response.getWriter();  
        
        MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
        Map map=re.getFileMap();

        //key 是input框中的 name 属性 value 得到的MultipartFile对象
        List fileList =new ArrayList();

        for( Object obj :map.values()){
         MultipartFile file=(MultipartFile)obj;
         fileList.add(file);
        }
  
        // 没有文件上传  
        if (fileList == null || fileList.size() == 0) {  
            out.println("please upload file first <p/>");  
            return null;  
        }  
  
        // 得到所有上传的文件  
        Iterator fileItr = fileList.iterator(); // 循环处理所有文件  
        while (fileItr.hasNext()) {  
        	MultipartFile fileItem = null;  
            String path = null;  
            long size = 0;  
            // 得到当前文件  
            fileItem = (MultipartFile) fileItr.next();  
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
            if (fileItem == null || fileItem.isEmpty()) {  
                continue; // 得到文件的完整路径  
            }  
            path = fileItem.getOriginalFilename();  
            // 得到文件的大小  
            size = fileItem.getSize();  
            if ("".equals(path) || size == 0 || size > MAX_SIZE) {  
                out.println("<script type=\"text/javascript\">alert('catch error during file upload as file is too large,please try again!');</script>"); // return; 
                log.error("文件超过最大值！");
                return null;
            }  
            // 得到去除路径的文件名  
            String t_name = path.substring(path.lastIndexOf("\\") + 1);  
            // 得到文件的扩展名(无扩展名时将得到全名)  
            String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
            // 拒绝接受规定文件格式之外的文件类型  
            if (t_ext!=null && allowedExt.indexOf(t_ext.toLowerCase())==-1) {  
                out.println("please upload allowed file with correct type <p/>");  
                return null;
            }  
            String datestr =DateUtils.getCurrentDateTime("yyyyMMddHHmmss")+RandomUtils.getRandomStr(6);
            String filePath= saveUrl+"t_"+datestr+"."+t_ext;
            try { // 保存文件  
                fileItem.transferTo(new File( filePath )); 
                out.print(savePath+"t_"+datestr+"."+t_ext); 
            } catch (Exception e) {  
                e.printStackTrace();  
            }
//            添加水印
        }  
        log.error("结束上传 banner图片");
        return null;
    }
	
	/**
	 * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
	 * @param isWaterMark
	 *  isWaterMark =yes ,添加水印 ， isWaterMark = 其他不加水印
	 *  @return
	 */
	public void addWaterMark(String isWaterMark,String srcImgPath,String targerPath){
		if(StringUtil.isNotEmpty( isWaterMark )){
			String iconPath = home_path_prefix + resource_path + "waterIcon.png";
        	if(isWaterMark.trim().equals("yes")){
        		WaterMarkUtils waterMarkUtils = new WaterMarkUtils();
        		waterMarkUtils.markImageByIcon(iconPath, srcImgPath, targerPath);
        	}
        }
	}

	/**
	 * banner图上传
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload/kindEditor", method = RequestMethod.POST)
	public String kindEditor(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String saveUrl = homePath + resourcePath + "banner/";
		String savePath = resourcePath + "banner/";
        final long MAX_SIZE = 10*1024*1024;  
        // 允许上传的文件格式的列表 final  
        String allowedExt = new String("*.png;*.jpg");  
        response.setContentType("text/html"); // 设置字符编码为UTF-8, 这样支持汉字显示  
        response.setCharacterEncoding("UTF-8");  
        // 实例化一个硬盘文件工厂,用来配置上传组件  
        DiskFileItemFactory dfif = new DiskFileItemFactory();  
        // 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
        dfif.setSizeThreshold(4096); // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
        File tmpFile = new File(saveUrl);  
        if (!tmpFile.exists()) {  
            tmpFile.mkdirs();  
        }  
        dfif.setRepository(tmpFile);  
  
        PrintWriter out = response.getWriter();  
        
        MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
        Map map=re.getFileMap();

        //key 是input框中的 name 属性 value 得到的MultipartFile对象
        List fileList =new ArrayList();

        for( Object obj :map.values()){
         MultipartFile file=(MultipartFile)obj;
         fileList.add(file);
        }
  
        // 没有文件上传  
        if (fileList == null || fileList.size() == 0) {  
            out.println("please upload file first <p/>");  
            return null;  
        }  
  
        // 得到所有上传的文件  
        Iterator fileItr = fileList.iterator(); // 循环处理所有文件  
        while (fileItr.hasNext()) {  
        	MultipartFile fileItem = null;  
            String path = null;  
            long size = 0;  
            // 得到当前文件  
            fileItem = (MultipartFile) fileItr.next();  
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
            if (fileItem == null || fileItem.isEmpty()) {  
                continue; // 得到文件的完整路径  
            }  
            path = fileItem.getOriginalFilename();  
            // 得到文件的大小  
            size = fileItem.getSize();  
            if ("".equals(path) || size == 0 || size > MAX_SIZE) {  
                out.println("<script type=\"text/javascript\">alert('catch error during file upload as file is too large,please try again!');</script>"); // return; 
                return null;
            }  
            // 得到去除路径的文件名  
            String t_name = path.substring(path.lastIndexOf("\\") + 1);  
            // 得到文件的扩展名(无扩展名时将得到全名)  
            String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
            // 拒绝接受规定文件格式之外的文件类型  
            if (t_ext!=null && allowedExt.indexOf(t_ext.toLowerCase())==-1) {  
                out.println("please upload allowed file with correct type <p/>");  
                return null;
            }  
            try { // 保存文件  
            	String datestr =DateUtils.getCurrentDateTime("yyyyMMddHHmmss")+RandomUtils.getRandomStr(6);
                fileItem.transferTo(new File(saveUrl+"t_"+datestr+"."+t_ext)); 
                Map<String,Object> rmap = new HashMap<String, Object>();
                rmap.put("error", 0);
                rmap.put("url",host+savePath+"t_"+datestr+"."+t_ext);
                out.print(JSONObject.fromObject(rmap).toString()); 
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        
        return null;
    }
	
	  /**
	 *上传图片
	 * @param hwb
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload/uploadImage", method = RequestMethod.POST)
	public static Map<String,String> uploadImage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 log.error("开始 上传 图片");
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String savePath = resourcePath + "userImage/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String thumbnailPath = resourcePath + "thumbnailImage/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String saveUrl = homePath + savePath;
        final long MAX_SIZE = 1024*1024;  
        // 允许上传的文件格式的列表 final  
        String allowedExt = new String("*.png;*.gif;*.jpg;");  
        response.setContentType("text/html"); // 设置字符编码为UTF-8, 这样支持汉字显示  
        response.setCharacterEncoding("UTF-8");  
        // 实例化一个硬盘文件工厂,用来配置上传组件  
        DiskFileItemFactory dfif = new DiskFileItemFactory();  
        // 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
        dfif.setSizeThreshold(4096); // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
        File tmpFile = new File(saveUrl);  
        log.error("saveUrl=" + saveUrl);
        if (!tmpFile.exists()) {  
            tmpFile.mkdirs();  
        }  
        File tmpFileTo = new File(homePath+thumbnailPath);  
        log.error("tmpFileTo=" + tmpFileTo);
        if (!tmpFileTo.exists()) {  
        	tmpFileTo.mkdirs();  
        }  
        dfif.setRepository(tmpFile);  
  
        PrintWriter out = response.getWriter();  
        
        MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
        Map map=re.getFileMap();

        //key 是input框中的 name 属性 value 得到的MultipartFile对象
        List fileList =new ArrayList();

        for( Object obj :map.values()){
         MultipartFile file=(MultipartFile)obj;
         fileList.add(file);
        }
  
        // 没有文件上传  
        if (fileList == null || fileList.size() == 0) {  
            return null;  
        }  
  
        // 得到所有上传的文件  
        Iterator fileItr = fileList.iterator(); // 循环处理所有文件  
        while (fileItr.hasNext()) {  
        	MultipartFile fileItem = null;  
            String path = null;  
            long size = 0;  
            // 得到当前文件  
            fileItem = (MultipartFile) fileItr.next();  
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
            if (fileItem == null || fileItem.isEmpty()) {  
                continue; // 得到文件的完整路径  
            }  
            path = fileItem.getOriginalFilename();  
            // 得到文件的大小  
            size = fileItem.getSize();  
            if ("".equals(path) || size == 0 || size > MAX_SIZE) {  
            	log.error("文件超过了= " + MAX_SIZE + "M,请重新选择图片");
                return null;
            }  
            // 得到去除路径的文件名  
            String t_name = path.substring(path.lastIndexOf("\\") + 1);  
            // 得到文件的扩展名(无扩展名时将得到全名)  
            String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
            // 拒绝接受规定文件格式之外的文件类型  
            if (t_ext!=null && allowedExt.indexOf(t_ext.toLowerCase())==-1) {  
            	log.error("不支持的类型 = " + t_ext);
                return null;
            }  
            try { // 保存文件  
            	String datestr =DateUtils.getCurrentDateTime("yyyyMMddHHmmss")+RandomUtils.getRandomStr(6);
                String filePath = saveUrl+"t_"+datestr+"."+t_ext;
                String filePathTo = homePath+thumbnailPath+"t_"+datestr+"."+t_ext;
            	fileItem.transferTo(new File(filePath)); 
            	ImgCompressUtil imgCom = new ImgCompressUtil(filePath,filePathTo);  
    	        imgCom.resizeFix(imgCom.getWidth()/3,imgCom.getHeight()/3);  
    	        Map<String,String> rMap = new HashMap<String, String>();
    	        rMap.put("url", savePath+"t_"+datestr+"."+t_ext);
    	        rMap.put("thumbnail", thumbnailPath+"t_"+datestr+"."+t_ext);
                out.print(JSONObject.fromObject(rMap).toString()); 
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return null;
		
	}
	
	
	
	 /**
	 * 手机 app 上传图片到后台
	 * @param hwb
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public List<Map<String,String>> uploadAppImage(HttpServletRequest request) throws Exception {
		
		log.error("app upload image  begin");
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>();
		Map<String,String> returnMap = new HashMap<String, String>();
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String savePath = resourcePath + "userImage/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String thumbnailPath = resourcePath + "thumbnailImage/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String saveUrl = homePath + savePath;
        final long MAX_SIZE = 10*1024*1024;  
        // 允许上传的文件格式的列表 final  
        String allowedExt = new String("*.png;*.gif;*.jpg;*.jpeg;");  
        // 实例化一个硬盘文件工厂,用来配置上传组件  
        DiskFileItemFactory dfif = new DiskFileItemFactory();  
        // 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
        dfif.setSizeThreshold(4096); // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
        File tmpFile = new File(saveUrl);  
        log.error("saveUrl=" + saveUrl);
        if (!tmpFile.exists()) {  
            tmpFile.mkdirs();  
        }  
        File tmpFileTo = new File(homePath+thumbnailPath);  
        log.error("tmpFileTo=" + tmpFileTo);
        if (!tmpFileTo.exists()) {  
        	tmpFileTo.mkdirs();  
        }  
        dfif.setRepository(tmpFile);  
        
        MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
        Map map=re.getFileMap();

        //key 是input框中的 name 属性 value 得到的MultipartFile对象
        List fileList =new ArrayList();

        for( Object obj :map.values()){
         MultipartFile file=(MultipartFile)obj;
         fileList.add(file);
        }
  
        // 没有文件上传  
        if (fileList == null || fileList.size() == 0) {  
            returnMap.put("error", "请选择文件");
            returnList.add(returnMap);
            return returnList;
        }  
  
        // 得到所有上传的文件  
        Iterator fileItr = fileList.iterator(); // 循环处理所有文件  
        while (fileItr.hasNext()) {  
        	
        	Map<String,String> fileReturnMap = new HashMap<String, String>();
        	MultipartFile fileItem = null;  
            String path = null;  
            long size = 0;  
            // 得到当前文件  
            fileItem = (MultipartFile) fileItr.next();  
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
            if (fileItem == null || fileItem.isEmpty()) {  
                continue; // 得到文件的完整路径  
            }  
            path = fileItem.getOriginalFilename();  
            // 得到文件的大小  
            size = fileItem.getSize();  
            if ("".equals(path) || size == 0 || size > MAX_SIZE) {  
            	log.error("文件超过了= " + MAX_SIZE + "M,请重新选择图片");
            	fileReturnMap.put("error", "文件过大，请重新选择图片");
            }  
            // 得到去除路径的文件名  
            String t_name = path.substring(path.lastIndexOf("\\") + 1);  
            // 得到文件的扩展名(无扩展名时将得到全名)  
            String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
            // 拒绝接受规定文件格式之外的文件类型  
            if (t_ext!=null && allowedExt.indexOf(t_ext.toLowerCase())==-1) {  
            	log.error("不支持的类型 = " + t_ext);
                returnMap.put("error", "不支持"+ t_ext +"类型 ");
            }  
            try { // 保存文件  
            	String datestr =DateUtils.getCurrentDateTime("yyyyMMddHHmmss")+RandomUtils.getRandomStr(6);
                String filePath = saveUrl+"t_"+datestr+"."+t_ext;
                String filePathTo = homePath+thumbnailPath+"t_"+datestr+"."+t_ext;
            	fileItem.transferTo(new File(filePath)); 
            	ImgCompressUtil imgCom = new ImgCompressUtil(filePath,filePathTo);  
    	        imgCom.resizeFix(imgCom.getWidth()/3,imgCom.getHeight()/3);  
    	       
    	        fileReturnMap.put("picUrl", savePath+"t_"+datestr+"."+t_ext);
    	        fileReturnMap.put("thumbnail", thumbnailPath+"t_"+datestr+"."+t_ext);
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            returnList.add(fileReturnMap);
        }  
        log.error("app upload image  success");
        return returnList;
	}
	
	
	 /**
	 * 上传设计师图片到后台
	 * @param hwb
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public List<Map<String,String>> uploadDesignerImage(HttpServletRequest request) throws Exception {
		
		log.error("app upload Designer image  begin");
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>();
		Map<String,String> returnMap = new HashMap<String, String>();
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String savePath = resourcePath + "designer/"+DateUtils.getCurrentDateTime("yyyyMM")+"/";
		String thumbnailPath = resourcePath + "designerThumbnail/"+DateUtils.getCurrentDateTime("yyyyMM")+"/";
		String saveUrl = homePath + savePath;
        final long MAX_SIZE = 10*1024*1024;  
        // 允许上传的文件格式的列表 final  
        String allowedExt = new String("*.png;*.gif;*.jpg;*.jpeg;");  
        // 实例化一个硬盘文件工厂,用来配置上传组件  
        DiskFileItemFactory dfif = new DiskFileItemFactory();  
        // 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
        dfif.setSizeThreshold(4096); // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
        File tmpFile = new File(saveUrl);  
        log.error("saveUrl=" + saveUrl);
        if (!tmpFile.exists()) {  
            tmpFile.mkdirs();  
        }  
        File tmpFileTo = new File(homePath+thumbnailPath);  
        log.error("tmpFileTo=" + tmpFileTo);
        if (!tmpFileTo.exists()) {  
        	tmpFileTo.mkdirs();  
        }  
        dfif.setRepository(tmpFile);  
        
        MultipartHttpServletRequest re=(MultipartHttpServletRequest)request;
        Map map=re.getFileMap();

        //key 是input框中的 name 属性 value 得到的MultipartFile对象
        List fileList =new ArrayList();

        for( Object obj :map.values()){
         MultipartFile file=(MultipartFile)obj;
         fileList.add(file);
        }
  
        // 没有文件上传  
        if (fileList == null || fileList.size() == 0) {  
            returnMap.put("error", "请选择文件");
            returnList.add(returnMap);
            return returnList;
        }  
  
        // 得到所有上传的文件  
        Iterator fileItr = fileList.iterator(); // 循环处理所有文件  
        while (fileItr.hasNext()) {  
        	
        	Map<String,String> fileReturnMap = new HashMap<String, String>();
        	MultipartFile fileItem = null;  
            String path = null;  
            long size = 0;  
            // 得到当前文件  
            fileItem = (MultipartFile) fileItr.next();  
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
            if (fileItem == null || fileItem.isEmpty()) {  
                continue; // 得到文件的完整路径  
            }  
            path = fileItem.getOriginalFilename();  
            // 得到文件的大小  
            size = fileItem.getSize();  
            if ("".equals(path) || size == 0 || size > MAX_SIZE) {  
            	log.error("文件超过了= " + MAX_SIZE + "M,请重新选择图片");
            	fileReturnMap.put("error", "文件过大，请重新选择图片");
            }  
            // 得到去除路径的文件名  
            String t_name = path.substring(path.lastIndexOf("\\") + 1);  
            // 得到文件的扩展名(无扩展名时将得到全名)  
            String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
            // 拒绝接受规定文件格式之外的文件类型  
            if (t_ext!=null && allowedExt.indexOf(t_ext.toLowerCase())==-1) {  
            	log.error("不支持的类型 = " + t_ext);
                returnMap.put("error", "不支持"+ t_ext +"类型 ");
            }  
            try { // 保存文件  
            	String datestr =DateUtils.getCurrentDateTime("yyyyMMddHHmmss")+RandomUtils.getRandomStr(6);
                String filePath = saveUrl+"t_"+datestr+"."+t_ext;
                String filePathTo = homePath+thumbnailPath+"t_"+datestr+"."+t_ext;
            	fileItem.transferTo(new File(filePath)); 
            	ImgCompressUtil imgCom = new ImgCompressUtil(filePath,filePathTo);  
    	        imgCom.resizeFix(imgCom.getWidth()/3,imgCom.getHeight()/3);  
    	       
    	        fileReturnMap.put("url", savePath+"t_"+datestr+"."+t_ext);
    	        fileReturnMap.put("thumbnail", thumbnailPath+"t_"+datestr+"."+t_ext);
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            returnList.add(fileReturnMap);
        }  
        log.error("app upload Designer image  success");
        return returnList;
	}
	
	
}
