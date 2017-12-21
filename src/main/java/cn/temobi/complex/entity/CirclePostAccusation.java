package cn.temobi.complex.entity;

public class CirclePostAccusation extends IdEntity{

	private Long circlePostId;		//帖子ID
	private String circlePostText;	//帖子内容
	private String circlePostTitle;	//帖子标题
	
	private Long circlePostDiscId;	//评论ID
	private String circlePostDiscText;	//评论内容
	
	private Long userId;			//被举报用户id
	private String userNick;		//被举报昵称
	private Long accusationUserId;	//举报人
	private String accusationUserNick;	//举报人昵称
	private String type;			//举报类型
	private int isDistort;			// 0 未处理   1 不属实 2 属实
	private String createdWhen;
	
	
	public Long getCirclePostId() {
		return circlePostId;
	}
	public void setCirclePostId(Long circlePostId) {
		this.circlePostId = circlePostId;
	}
	public Long getCirclePostDiscId() {
		return circlePostDiscId;
	}
	public void setCirclePostDiscId(Long circlePostDiscId) {
		this.circlePostDiscId = circlePostDiscId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAccusationUserId() {
		return accusationUserId;
	}
	public void setAccusationUserId(Long accusationUserId) {
		this.accusationUserId = accusationUserId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIsDistort() {
		return isDistort;
	}
	public void setIsDistort(int isDistort) {
		this.isDistort = isDistort;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getCirclePostText() {
		return circlePostText;
	}
	public void setCirclePostText(String circlePostText) {
		this.circlePostText = circlePostText;
	}
	public String getCirclePostTitle() {
		return circlePostTitle;
	}
	public void setCirclePostTitle(String circlePostTitle) {
		this.circlePostTitle = circlePostTitle;
	}
	public String getCirclePostDiscText() {
		return circlePostDiscText;
	}
	public void setCirclePostDiscText(String circlePostDiscText) {
		this.circlePostDiscText = circlePostDiscText;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getAccusationUserNick() {
		return accusationUserNick;
	}
	public void setAccusationUserNick(String accusationUserNick) {
		this.accusationUserNick = accusationUserNick;
	}
	
	
	
}
