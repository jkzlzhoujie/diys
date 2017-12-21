package cn.temobi.complex.dto;

import java.io.Serializable;
import java.util.List;

import cn.temobi.complex.entity.PmProductCollectPic;

@SuppressWarnings("serial")
public class PmProductInfoDto implements Serializable{
	
	private Long productId;
	private Long clientId;//客户端ID
	private Long userId;//客户端ID
	private String url;//url
	private String thumbnail;//thumbnail
	private String createdWhen;//创建时间
	private int searchCount;
	private int praiseCount;
	private int downloadCount;
	private int shareCount;
	private int discussCount;
	private int editCount;
	private int psCount;
	private int stampCount;
	private int hotScore;
	private int magicScore;
	private String productLabel;
	private String nickName;
	private String headImageUrl;
	private String machine;
	private int level;
	private int sex;
	private String city;
	private String description;
	private String depict;
	private String productName;
	private String createFrom; //0、系统后台1、用户
	private Long typeId;
	private String typeName;
	private int isZ;
	private int isC;
	private int isPublic;
	private int createType;
	private int jumpType; //0 详情页 1 H5页 2 url地址
	private String jumpText; //跳转的内容
	private Long collectId; //收藏表Id
	private int isCollect;	//1 已收藏 0 未收藏
	private List<PmProductCollectPic> pmProductCollectPicList;//作品相册
	private int picCollectCount ;//图片集  图片数量
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getIsZ() {
		return isZ;
	}
	public void setIsZ(int isZ) {
		this.isZ = isZ;
	}
	public int getIsC() {
		return isC;
	}
	public void setIsC(int isC) {
		this.isC = isC;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateFrom() {
		return createFrom;
	}
	public void setCreateFrom(String createFrom) {
		this.createFrom = createFrom;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getCreateType() {
		return createType;
	}
	public void setCreateType(int createType) {
		this.createType = createType;
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
	public Long getCollectId() {
		return collectId;
	}
	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}
	public int getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}
	public List<PmProductCollectPic> getPmProductCollectPicList() {
		return pmProductCollectPicList;
	}
	public void setPmProductCollectPicList(
			List<PmProductCollectPic> pmProductCollectPicList) {
		this.pmProductCollectPicList = pmProductCollectPicList;
	}
	public int getPicCollectCount() {
		return picCollectCount;
	}
	public void setPicCollectCount(int picCollectCount) {
		this.picCollectCount = picCollectCount;
	}
}
