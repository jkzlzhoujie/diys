package cn.temobi.complex.entity;

public class CmUserMessage extends IdEntity{

	private Long userId;	//用户ID
	private int type;//0 作品发表审核通过 1 作品被赞  2 作品被踩 3 作品被分享 4 作品被评论 5 作品被下载 6 作品被举报    
				// 7 当用户对某个作品进行以上操作时，都需要在该表中写入消息，在content字段组合要显示的消息内容，
                //   显示消息列表的时候只先显示 content字段的内容，点进去再看具体的操作内容。',
				//8 帖子赞  9 帖子评论 10 帖子的举报
	private Long productId;  //作品ID，帖子ID
	private String productUrl;
	private Long relId;			//关联操作id
	private Long sendUserId;	//消息发送用户
	private String content;		//消息内容
	private int isRead;			//'0 未读 1 已读
	private String createdWhen;
	private String readWhen;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getRelId() {
		return relId;
	}
	public void setRelId(Long relId) {
		this.relId = relId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getReadWhen() {
		return readWhen;
	}
	public void setReadWhen(String readWhen) {
		this.readWhen = readWhen;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public Long getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}
}
