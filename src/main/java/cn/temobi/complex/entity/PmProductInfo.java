package cn.temobi.complex.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PmProductInfo extends IdEntity{
	private Long productId;//作品Id,兼容原接口
	private Long userId;//用户ID
	private Long clientId;//客户端ID
	private String resourceId;//客户端ID
	private String url;//url
	private String thumbnail;//thumbnail
	
	private String createdWhen;	//创建时间
	private int createType; 	//0 原创  1 二次创作二次创作需要保存原始作品id
	private int jumpType; 		//0 详情页 1 H5页 2 url地址
	private String jumpText; 	//跳转的内容
	private String createFrom; 	//0、系统后台1、用户
	private Long srcProductId;	//原作品ID
	
	private int hotScore;//分值计算公式：1*查看数+2*点赞数+3*下载数+5*分享数+2*评论数+（-2）*踩数
	private int magicScore;//分值计算公式：2*点赞数+3*下载数+5*分享数+5*评论数+2*踩数
	private int hotSystemScore;		//热度系统分值   系统热度分值，用于管理调节，直接累加到hot_score
	private int magicSystemScore;	//神图系统分值  神图系统分值
	
	private int searchCount;	//查看数
	private int praiseCount; 	//点赞数
	private int downloadCount;	//下载数
	private int shareCount;		//分享数
	private int discussCount;	//讨论数
	private int editCount;		//二次编辑数
	private int psCount;		//p图记录数
	private int stampCount;		//点踩数
	private int audit;			//0 未审核  1 已审核  2  被举报
	private int isPublic;  		
//								0 否，完全不公开
//								1 公开到大厅，只有为1的时候，首页才可以查询到
//								2 公开到求p专题列表，
//								3 公开到私人订制专题
//								4 公开到大画家专题
//							
//								98 作者可见
//								99 删除

	private int typeId;			//0 未指定，默认 其他对应作品类型表
	private String productLabel;//作品标签
	private String productName;	//作品名称
	private String description;	//作品描述
	private String depict;
	private String createLocation;//创作位置
	private int picCollectFlag ;//图片集标识 1 是相册
	private int picCollectCount ;//图片集  图片数量
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public int getCreateType() {
		return createType;
	}
	public void setCreateType(int createType) {
		this.createType = createType;
	}
	public Long getSrcProductId() {
		return srcProductId;
	}
	public void setSrcProductId(Long srcProductId) {
		this.srcProductId = srcProductId;
	}
	public int getHotScore() {
		return hotScore;
	}
	public void setHotScore(int hotScore) {
		this.hotScore = hotScore;
	}
	public int getMagicScore() {
		return magicScore;
	}
	public void setMagicScore(int magicScore) {
		this.magicScore = magicScore;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public int getDiscussCount() {
		return discussCount;
	}
	public void setDiscussCount(int discussCount) {
		this.discussCount = discussCount;
	}
	public int getEditCount() {
		return editCount;
	}
	public void setEditCount(int editCount) {
		this.editCount = editCount;
	}
	public int getPsCount() {
		return psCount;
	}
	public void setPsCount(int psCount) {
		this.psCount = psCount;
	}
	public int getStampCount() {
		return stampCount;
	}
	public void setStampCount(int stampCount) {
		this.stampCount = stampCount;
	}
	public String getProductLabel() {
		return productLabel;
	}
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}
	public String getCreateLocation() {
		return createLocation;
	}
	public void setCreateLocation(String createLocation) {
		this.createLocation = createLocation;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getAudit() {
		return audit;
	}
	public void setAudit(int audit) {
		this.audit = audit;
	}
	public int getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getHotSystemScore() {
		return hotSystemScore;
	}
	public void setHotSystemScore(int hotSystemScore) {
		this.hotSystemScore = hotSystemScore;
	}
	public int getMagicSystemScore() {
		return magicSystemScore;
	}
	public void setMagicSystemScore(int magicSystemScore) {
		this.magicSystemScore = magicSystemScore;
	}
	public String getCreateFrom() {
		return createFrom;
	}
	public void setCreateFrom(String createFrom) {
		this.createFrom = createFrom;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public Long getProductId() {
		return this.id;
	}
	public int getJumpType() {
		return jumpType;
	}
	public void setJumpType(int jumpType) {
		this.jumpType = jumpType;
	}
	public String getJumpText() {
		return jumpText;
	}
	public void setJumpText(String jumpText) {
		this.jumpText = jumpText;
	}
	public int getPicCollectFlag() {
		return picCollectFlag;
	}
	public void setPicCollectFlag(int picCollectFlag) {
		this.picCollectFlag = picCollectFlag;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getPicCollectCount() {
		return picCollectCount;
	}
	public void setPicCollectCount(int picCollectCount) {
		this.picCollectCount = picCollectCount;
	}
	
	
}
