package cn.temobi.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.ChannelSftp.LsEntrySelector;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SftpUtil {
	private static final Logger log = Logger.getLogger(SftpUtil.class);
//	private String host = "";
//	private int port = 22;
//	private String username = "";
//	private String password = "";
//	
	private static Session session = null;
	private static ChannelSftp channelStfp = null;
	
	/**
	 * 打开连接
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param timeout
	 * @return
	 * @throws JSchException
	 */
	public static ChannelSftp connect(String host, int port, String username,
			String password, int timeout) throws JSchException {
		JSch jsch = new JSch(); // 创建JSch对象
		session = jsch.getSession(username, host, port); // 根据用户名，主机ip，端口获取一个Session对象
		log.debug("Session created.");
		if (password != null) {
			session.setPassword(password); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(timeout); // 设置timeout时间
		session.connect(); // 通过Session建立链接
		log.debug("Session connected.");

		log.debug("Opening Channel.");
		Channel channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		log.debug("Connected successfully to ftpHost = " + host
				+ ",as ftpUserName = " + username + ", returning: " + channel);
		channelStfp = (ChannelSftp) channel;
		return channelStfp;
	}
	
	/**
	 * 断开连接
	 */
	public static void disConnect(){
		if(channelStfp != null){
			channelStfp.disconnect();
		}
		if(session != null){
			session.disconnect();
		}
	}
	
	/**
	 * 查看文件目录
	 * @param path
	 * @param selector
	 * @return
	 * @throws Exception
	 */
	public static List<String> ls(String path,LsEntrySelector selector)throws Exception{
		List<String> fileList = new ArrayList<String>();
		if(!SftpUtil.channelStfp.isConnected()){
			throw new Exception("channelSftp disconnected");
		}
		Vector<LsEntry> lsEntrys = SftpUtil.channelStfp.ls(path);
		for(LsEntry e:lsEntrys){
			if(selector.select(e) >0){
				fileList.add(e.getFilename());
			}
		}
		return fileList;
	}
	
	/**
	 * 下载文件
	 * @param src
	 * @param dst
	 * @return
	 * @throws Exception
	 */
	public static boolean download(String src,String dst)throws Exception{
		if(!SftpUtil.channelStfp.isConnected()){
			throw new Exception("channelSftp disconnected");
		}
		SftpUtil.channelStfp.get(src, dst);
		log.info("download completed");
		return true;
	}
	
	/**
	 * 删除文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean rm(String path)throws Exception{
		if(!SftpUtil.channelStfp.isConnected()){
			throw new Exception("channelSftp disconnected");
		}
		SftpUtil.channelStfp.rm(path);
		return true;
	}
	
	/**
	 * 上传文件
	 * @param src
	 * @param dst
	 * @return
	 * @throws Exception
	 */
	public static boolean upload(String src,String dst)throws Exception{
		if(!SftpUtil.channelStfp.isConnected()){
			throw new Exception("channelSftp disconnected");
		}
		SftpUtil.channelStfp.put(src, dst);
		log.info("upload completed");
		return true;
	}
	
	public static void main(String[] args)throws Exception {
		ChannelSftp channel = SftpUtil.connect("112.5.166.203", 36200, "temobi", "temobi@#", 60000);
		
//		List<String> files = SftpUtil.ls("/home/temobi/", new LsEntrySelector() {
//			
//			@Override
//			public int select(LsEntry entry) {
//				if(entry.getFilename().endsWith(".war")){
//					return 1;
//				}else{
//					return 0;
//				}
//				
//			}
//		});
		channel.cd("/home/temobi/temp");
//		System.out.println(Arrays.toString(files.toArray()));
//		System.out.println(new Date());
//		SftpUtil.download("emoji.war", "D:/home/");
////		SftpUtil.upload("D:/home/log.log", "/home/temobi/");
////		SftpUtil.rm("log.log");
//		System.out.println(new Date());
//		SftpUtil.disConnect();
	}
	
}
