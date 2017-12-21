package cn.temobi.complex.entity;

public class CmCircle extends IdEntity{

	private Long clientId;			//客户端id
	private Long userId;			//用户id
	private String name;			//圈子名称
	private String depict;			//圈子描述
	private String logo;			//圈子图标
	private String thumbnail;		//缩略图
	
	private String addTime;			//创建时间
	private String flag;			//0 正常 1用户解散 2管理员删除
	private String latestImage;		//最新图片
	private Long latestProductId;	//最新图片ID
	private int imageNum;			//作品数
	private int userNum;			//作者数
	private int discussNum;			//评论数
	private int followNum;			//关注数
	private String isFollow;		//是否关注 1 已关注 0 未关注
	private String nickName;		//昵称
	private String headImageUrl;   //头像url
	private int type ;				//类型
	private String typeName;		//类型名称
	private Integer isRecommend ;		//是否推荐  1 推荐 0 不推荐
	private Integer placeRecommend ;	//推荐位置 
	
	
	
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getImageNum() {
		return imageNum;
	}
	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public int getDiscussNum() {
		return discussNum;
	}
	public void setDiscussNum(int discussNum) {
		this.discussNum = discussNum;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Long getLatestProductId() {
		return latestProductId;
	}
	public void setLatestProductId(Long latestProductId) {
		this.latestProductId = latestProductId;
	}
	public String getLatestImage() {
		return latestImage;
	}
	public void setLatestImage(String latestImage) {
		this.latestImage = latestImage;
	}
	public String getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}
	public int getFollowNum() {
		return followNum;
	}
	public void setFollowNum(int followNum) {
		this.followNum = followNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getPlaceRecommend() {
		return placeRecommend;
	}
	public void setPlaceRecommend(Integer placeRecommend) {
		this.placeRecommend = placeRecommend;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
}
