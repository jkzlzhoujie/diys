package cn.temobi.complex.entity;

import java.util.List;

import cn.temobi.core.util.CommonUtil;

/**
 */
@SuppressWarnings("serial")
public class PmTopicJoinProducts extends IdEntity{
	private Long topicId;		//专题id
	private int journalSeq;
	private Long productId;		//作品id
	private Long accountBuyId;	//购买ID
	private Long userId;		//参与用户id
	private String joinType;		
						//'0、未指定
					    //	  对于求P专题，指定求P类型
						// 1、求网友P  2、求设计师P, 如果同时选项多个，用逗号分隔，如1,2,
						//   对于私人订制，该字段指定定制的类型
						// 1、Q版  2 彩绘  如果同时选中多个，用逗号分并，例如 1,2,
						//类型保存不超过9个，即从1-9，',
	private int joinProducts;
	private int joinUser;
	private Long acceptUserId;
	private String distributeUser;
	private String acceptUserName;
	private String description;
	private String depict;
	private String contact;
	private String qqContact;
	private String acceptRemark;
	private String joinTime;
	private String url;
	private int praiseCount;
	private int searchCount;
	private int discussCount;
	private int shareCount;
	private int downloadCount;
	private int stampCount;
	private String thumbnail;
	private String spUrl;
	private String spThumbnail;
	private String headImageUrl;
	private String nickName;
	private String distributeNo;
	private String orderNo;
	private String status = "0";		//0未支付1 已支付2支付失败3已取消 4已删除
	private String designUrl;
	private Long psId;					//获奖P图
	private String updateTime;			//获奖时间
	private String getType;				//1用户主动设置 2系统设置
	private String channel;
	private String isGet = "0";			//是否已被采纳0否 1是2超时
	private double price;
	private int isZ;
	private int isC;
	private String phoneShell;		//手机壳
	private String receivePerson;	//收货人
	private String receiveAddress;	//收货地址
	private int isPri;				//是否是专属悬赏单 0 否 1是
	private Long collectId; 		//收藏表Id
	private int isCollect;	//1 已收藏 0 未收藏
	private List<PmProductLabel> pmproductLabelList;//P图悬赏的标签
	
	public String getPhoneShell() {
		return phoneShell;
	}
	public void setPhoneShell(String phoneShell) {
		this.phoneShell = phoneShell;
	}
	public String getReceivePerson() {
		return receivePerson;
	}
	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public int getJournalSeq() {
		return journalSeq;
	}
	public void setJournalSeq(int journalSeq) {
		this.journalSeq = journalSeq;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public int getJoinProducts() {
		return joinProducts;
	}
	public void setJoinProducts(int joinProducts) {
		this.joinProducts = joinProducts;
	}
	public Long getAcceptUserId() {
		return acceptUserId;
	}
	public void setAcceptUserId(Long acceptUserId) {
		this.acceptUserId = acceptUserId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAcceptRemark() {
		return acceptRemark;
	}
	public void setAcceptRemark(String acceptRemark) {
		this.acceptRemark = acceptRemark;
	}
	public String getDistributeNo() {
		return distributeNo;
	}
	public void setDistributeNo(String distributeNo) {
		this.distributeNo = distributeNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesignUrl() {
		return designUrl;
	}
	public void setDesignUrl(String designUrl) {
		this.designUrl = designUrl;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public String getSpUrl() {
		return spUrl;
	}
	public void setSpUrl(String spUrl) {
		this.spUrl = spUrl;
	}
	public String getSpThumbnail() {
		return spThumbnail;
	}
	public void setSpThumbnail(String spThumbnail) {
		this.spThumbnail = spThumbnail;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public int getDiscussCount() {
		return discussCount;
	}
	public void setDiscussCount(int discussCount) {
		this.discussCount = discussCount;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public int getStampCount() {
		return stampCount;
	}
	public void setStampCount(int stampCount) {
		this.stampCount = stampCount;
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
	public String getQqContact() {
		return qqContact;
	}
	public void setQqContact(String qqContact) {
		this.qqContact = qqContact;
	}
	public Long getPsId() {
		return psId;
	}
	public void setPsId(Long psId) {
		this.psId = psId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getGetType() {
		return getType;
	}
	public void setGetType(String getType) {
		this.getType = getType;
	}
	public String getIsGet() {
		return isGet;
	}
	public void setIsGet(String isGet) {
		this.isGet = isGet;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Long getAccountBuyId() {
		return accountBuyId;
	}
	public void setAccountBuyId(Long accountBuyId) {
		this.accountBuyId = accountBuyId;
	}
	public String getAcceptUserName() {
		return acceptUserName;
	}
	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public int getJoinUser() {
		return joinUser;
	}
	public void setJoinUser(int joinUser) {
		this.joinUser = joinUser;
	}
	public String getDistributeUser() {
		return distributeUser;
	}
	public void setDistributeUser(String distributeUser) {
		this.distributeUser = distributeUser;
	}
	public int getIsPri() {
		return isPri;
	}
	public void setIsPri(int isPri) {
		this.isPri = isPri;
	}
	public List<PmProductLabel> getPmproductLabelList() {
		return pmproductLabelList;
	}
	public void setPmproductLabelList(List<PmProductLabel> pmproductLabelList) {
		this.pmproductLabelList = pmproductLabelList;
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
}
