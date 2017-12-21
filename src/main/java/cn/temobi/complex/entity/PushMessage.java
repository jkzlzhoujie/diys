package cn.temobi.complex.entity;

public class PushMessage {

	/**
	 * 推送对象别名
	 */
	private String alias;
	/**
	 * 消息标题
	 */
	private String msgTitle;
	/**
	 * 消息内容
	 */
	private String msgContent;
	/**
	 * 验证码
	 */
	private String validCode;
	
	private Long packId;
	
	
	public Long getPackId() {
		return packId;
	}
	public void setPackId(Long packId) {
		this.packId = packId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
}
