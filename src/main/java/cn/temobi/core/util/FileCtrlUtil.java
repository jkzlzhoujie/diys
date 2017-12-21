package cn.temobi.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class FileCtrlUtil {
	public static void delFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
	}
	
	public static void delDir(File srcFile){
		if(srcFile.exists()){
			System.out.println("111");
			srcFile.delete();
		}
	}
	
	public static void createDir(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public static File createFile(String filePath){
		return new File(filePath);
	}
	
	public static void delRecursion(File file){
		if(!file.exists()){
			return;
		}
		if(!file.isDirectory()){
			file.delete();
			return;
		}
		File[] subfiles = file.listFiles();
		for(File f:subfiles){
			delRecursion(f);
		}
		file.delete();
	}
	
	// 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	
	// 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                // 文件名去掉后缀
//                String fileNameNoSuffix = file[i].getName().substring(0, file[i].getName().lastIndexOf("."));
//                File targetFile = new File(new File(targetDir).getAbsolutePath() + "/" + fileNameNoSuffix);
                File targetFile = new File(new File(targetDir).getAbsolutePath() + "/" + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }
    
    public static String convertFileSize(long filesize) {
		String strUnit="Bytes";
		String strAfterComma="";
		int intDivisor=1;
		if(filesize>=1024*1024) {
		    strUnit = "MB";
		    intDivisor=1024*1024;
		}else if(filesize>=1024) {
		    strUnit = "KB";
		    intDivisor=1024;
		}
		if(intDivisor==1) return filesize + " " + strUnit;
		strAfterComma = "" + 100 * (filesize % intDivisor) / intDivisor ;
    	if(strAfterComma=="") strAfterComma=".0";
    	return filesize / intDivisor + "." + strAfterComma + " " + strUnit;
    } 
    
    public static void copyImage(String fromUrl, String toUrl) throws Exception {
    	  FileInputStream fi = new FileInputStream(fromUrl);
    	  BufferedInputStream in = new BufferedInputStream(fi);
    	  FileOutputStream fo = new FileOutputStream(toUrl);
    	  BufferedOutputStream out = new BufferedOutputStream(fo);
    	  byte[] buf = new byte[1024];
    	  int len = in.read(buf);//读文件，将读到的内容放入到buf数组中，返回的是读到的长度
    	  while(len != -1){
    	   out.write(buf, 0, len);
    	   len = in.read(buf);
    	  }
    	  out.close();
    	  fo.close();
    	  in.close();
    	  fi.close();
    }
    
    
    public static void main(String[] args) throws IOException {
//    	Map<String, Integer> relation = ZipUtil.deCompressFile("D:/mywork/emoji_new/src/main/webapp/rcs/rs/upload/xxh_30.zip", "D:/mywork/emoji_new/src/main/webapp/rcs/rs/upload/");
//    	System.out.println(relation.size());
    	
    	String aa = "/rcs/rs/upload/xxh_3020140521100235748.zip";
    	System.out.println(aa.substring(aa.lastIndexOf("/")+1, aa.lastIndexOf(".")));
    	
	}
}
