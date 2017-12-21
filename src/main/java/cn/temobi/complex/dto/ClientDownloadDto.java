package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientDownloadDto implements Serializable{
	private Long packageId;//表情包编号
	private String packageName;//表情包名
	private String packageUrl;//表情包下载地址
	private String packageZip;//Zip包描述
}
