package cn.temobi.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import cn.temobi.complex.entity.PmProductInfo;

public class ZipUtil {
	
	private static final String BASE_DIR = ""; 
	
	private static final int BUFFER = 1024;
	
	private static final String SEPARATOR = "/";
	

	/**
	 * 压缩
	 * @param list
	 * @throws Exception
	 */
	public static boolean compress(File srcFile,String zipDir, String zipPath) throws Exception{
		FileCtrlUtil.delFile(zipPath);
		FileCtrlUtil.createDir(zipDir);
		File zipFile = new File(zipPath);
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
		zos.setEncoding("GBK");
		compress(srcFile, zos, BASE_DIR);
		zos.flush();
		zos.close();
		FileCtrlUtil.delRecursion(srcFile);
		return true;
	}

	/**
	 * 压缩
	 * @param srcFile
	 * @param zos
	 * @param basePath
	 * @throws Exception
	 */
	public static void compress(File srcFile,ZipOutputStream zos,String basePath) throws Exception{
		if (srcFile.isDirectory()) {
            compressDir(srcFile, zos, basePath);  
        } else {  
            compressFile(srcFile, zos, basePath);  
        } 
	}

	/**
	 * 文件压缩
	 * @param file
	 * @param zos
	 * @throws Exception 
	 */
	private static void compressFile(File file, ZipOutputStream zos,String dir) throws Exception {
		// TODO Auto-generated method stub
		ZipEntry entry = new ZipEntry(dir+file.getName());
		zos.putNextEntry(entry);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int len;
		byte[] buffer = new byte[BUFFER];
		while((len=bis.read(buffer, 0, BUFFER))!=-1){
			zos.write(buffer, 0, len);
		}
		bis.close();
		zos.closeEntry();
	}

	/**
	 * 压缩目录
	 * @param file
	 * @param zos
	 * @throws Exception 
	 */
	private static void compressDir(File dir, ZipOutputStream zos,String basePath) throws Exception {
		// TODO Auto-generated method stub
		File[] files = dir.listFiles();
		if(files.length<1){
			ZipEntry entry = new ZipEntry(basePath+dir.getName()+SEPARATOR);
			zos.putNextEntry(entry);
			zos.closeEntry();
		}
		for(File file:files){
			//递归压缩
			compress(file, zos, basePath+dir.getName()+SEPARATOR);
		}
	}
	
	/**
	 * 解压文件
	 * @param zipFile
	 */
	public static Map<String,Integer> deCompressFile(String zipFilePath, String releasePath,Map<String,String> map)
			throws IOException {
		FileCtrlUtil.createDir(releasePath);
		Map<String,Integer> relation = new HashMap<String, Integer>();
		Set<String> set = new HashSet<String>();
		Integer count = 0;
		String temp = null;
		ZipFile zipFile = new ZipFile(zipFilePath, "GBK");
		Enumeration<ZipEntry> e = zipFile.getEntries();
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		ZipEntry zipEntry = null;
		String zipEntryName = null;
		String[] zipEntryNameArray = null;
		while (e.hasMoreElements()) {
			zipEntry = e.nextElement();
			zipEntryName = zipEntry.getName();
			if(StringUtil.isEmpty(zipEntryName)) continue;
			if(zipEntryName.indexOf(".") == -1) continue;
			zipEntryName = zipEntryName.substring(zipEntryName.indexOf("/")+1);
			zipEntryNameArray = zipEntryName.split("/");
			String path = releasePath;
			for (int i = 0; i < zipEntryNameArray.length; i++) {
				if(i!=zipEntryNameArray.length-1){
					temp = CnToSpell.getPinYinHeadChar(zipEntryNameArray[i]);
					path+=temp+SEPARATOR;
					FileCtrlUtil.createDir(path);
				}else if(!zipEntryName.endsWith(SEPARATOR)){
					
					String suffix = zipEntryName.substring(zipEntryName.lastIndexOf(".")+1, zipEntryName.length());
					map.put("suffix", suffix);
					inputStream = zipFile.getInputStream(zipEntry);
					String key = zipEntryName.substring(zipEntryName.lastIndexOf("/")+1, zipEntryName.lastIndexOf("."));
					Integer nowIndex = null;
					if(set.contains(key)){
						nowIndex = relation.get(key);
					}else{
						set.add(key);
						count++;
						nowIndex = count;
						relation.put(key, nowIndex);
					}
					if(nowIndex == null){
						zipEntryName = CnToSpell.getPinYinHeadChar(zipEntryName);
					}else{
						
						zipEntryName = CnToSpell.getPinYinHeadChar(zipEntryName.substring(0,zipEntryName.lastIndexOf("/")+1)) + nowIndex + zipEntryName.substring(zipEntryName.lastIndexOf("."),zipEntryName.length());
					}
					fileOutputStream = new FileOutputStream(
							FileCtrlUtil.createFile(releasePath
									+ zipEntryName));
					int len;
					byte[] buffer = new byte[BUFFER];
					while ((len = inputStream.read(buffer, 0, BUFFER)) != -1) {
						fileOutputStream.write(buffer, 0, len);
					}
					inputStream.close();
					fileOutputStream.close();
				}else {
					//生成空的文件夹
					temp = CnToSpell.getPinYinHeadChar(zipEntryNameArray[i]);
					path += temp+SEPARATOR;
					FileCtrlUtil.createDir(path);
				}
			}
		}
		zipFile.close();
		return relation;
	}
	
	/**
	 * 解压
	 * @param zipFilePath
	 * @param releasePath
	 * @throws IOException
	 */
	public static void deCompress(String zipFilePath, String releasePath)
			throws IOException {
		String temp = null;
		ZipFile zipFile = new ZipFile(zipFilePath, "GBK");
		Enumeration<ZipEntry> e = zipFile.getEntries();
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		ZipEntry zipEntry = null;
		String zipEntryName = null;
		String[] zipEntryNameArray = null;
		while (e.hasMoreElements()) {
			zipEntry = e.nextElement();
			zipEntryName = zipEntry.getName();
			zipEntryNameArray = zipEntryName.split("/");
			String path = releasePath;
			for (int i = 0; i < zipEntryNameArray.length; i++) {
				if(i!=zipEntryNameArray.length-1){
					temp = CnToSpell.getPinYinHeadChar(zipEntryNameArray[i]);
					path+=temp+SEPARATOR;
					FileCtrlUtil.createDir(path);
				}else if(!zipEntryName.endsWith(SEPARATOR)){
					inputStream = zipFile.getInputStream(zipEntry);
					fileOutputStream = new FileOutputStream(
							FileCtrlUtil.createFile(releasePath
									+ zipEntryName));
					int len;
					byte[] buffer = new byte[BUFFER];
					while ((len = inputStream.read(buffer, 0, BUFFER)) != -1) {
						fileOutputStream.write(buffer, 0, len);
					}
					inputStream.close();
					fileOutputStream.close();
				}else {
					//生成空的文件夹
					temp = CnToSpell.getPinYinHeadChar(zipEntryNameArray[i]);
					path += temp+SEPARATOR;
					FileCtrlUtil.createDir(path);
				}
			}
		}
		zipFile.close();
	}
	
	
	/**
	 * 解压
	 * @param zipFilePath
	 * @param releasePath
	 * @throws IOException
	 */
	public static void deCompressImage(String zipFilePath, String releasePath, String dePath,List<PmProductInfo> list)
			throws IOException {
		String homePath = FileUtil.getPathTomcat();
		String temp = null;
		ZipFile zipFile = new ZipFile(homePath+zipFilePath, "GBK");
		Enumeration<ZipEntry> e = zipFile.getEntries();
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		ZipEntry zipEntry = null;
		String zipEntryName = null;
		String[] zipEntryNameArray = null;
		while (e.hasMoreElements()) {
			zipEntry = e.nextElement();
			zipEntryName = zipEntry.getName();
			zipEntryNameArray = zipEntryName.split("/");
			String path = homePath+releasePath;
			String zipPath = path+ zipEntryName;
			PmProductInfo pmProductInfo = null;
			for (int i = 0; i < zipEntryNameArray.length; i++) {
				if(i!=zipEntryNameArray.length-1){
					temp = CnToSpell.getPinYinHeadChar(zipEntryNameArray[i]);
					path+=temp+SEPARATOR;
					FileCtrlUtil.createDir(path);
					FileCtrlUtil.createDir(homePath+dePath+temp+SEPARATOR);
				}else if(!zipEntryName.endsWith(SEPARATOR)){
					inputStream = zipFile.getInputStream(zipEntry);
					fileOutputStream = new FileOutputStream(FileCtrlUtil.createFile(zipPath));
					int len;
					byte[] buffer = new byte[BUFFER];
					while ((len = inputStream.read(buffer, 0, BUFFER)) != -1) {
						fileOutputStream.write(buffer, 0, len);
					}
					inputStream.close();
					fileOutputStream.close();
					
					ImgCompressUtil imgCom = new ImgCompressUtil(zipPath,homePath+dePath+zipEntryName);  
	    	        imgCom.resizeFix(imgCom.getWidth()/3,imgCom.getHeight()/3);  
	    	        pmProductInfo = new PmProductInfo();
	    	        pmProductInfo.setUrl(releasePath+zipEntryName);
	    	        pmProductInfo.setThumbnail(dePath+zipEntryName);
	    	        list.add(pmProductInfo);
				}else {
					//生成空的文件夹
					temp = CnToSpell.getPinYinHeadChar(zipEntryNameArray[i]);
					path += temp+SEPARATOR;
					FileCtrlUtil.createDir(path);
				}
			}
		}
		zipFile.close();
	}
	
	
	@SuppressWarnings("unused")
	public static String getKey(Map<String, Integer> map,int value){
		Integer v = value;
		String result = null;
		for(String key:map.keySet()){
			if(map.get(key).equals(v)){
				result = key;
				break;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		ZipFile zipFile;
		try {
			String path = "D:/mywork/emoji_new/src/main/webapp/rcs/rs/upload/t_20140521182542645279.zip";
//			String path = "D:/mywork/emoji_new/src/main/webapp/rcs/rs/cp_zip/nnt(qlzy)20140521172711222.zip";
//			String path = "D:/mywork/emoji_new/src/main/webapp/rcs/rs/cp_zip/CP1352/215_publish.zip";
			String path1 = "D:/mywork/emoji_new/src/main/webapp/rcs/rs/emoji/nnt(qlzy)20140521172711222/";
//			zipFile = new ZipFile(path, "GBK");
//			Enumeration<ZipEntry> e = zipFile.getEntries();
//			while(e.hasMoreElements()) {
//				System.out.println(e.nextElement().getName());
//			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
