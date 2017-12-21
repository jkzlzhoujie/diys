package cn.temobi.complex.dto;

import java.util.List;

import cn.temobi.complex.entity.IdEntity;
import cn.temobi.complex.entity.PmProductLabel;
import cn.temobi.complex.entity.PmTopicPsProducts;

/**
 */
@SuppressWarnings("serial")
public class PIndexDto extends IdEntity{
	private Long joinId;
	private Long productId;
	private Long userId;
	private String depict;
	private String addTime;
	private int totalPraise;
	private int totalDiscuss;
	private int totalShare;
	private int totalProduct;
	private int totalUser;
	private String url;
	private String thumbnail;
	private String nickName;
	private String headImageUrl;
	private String price;
	private String isGet;
	private int isZ;
	private Long collectId; 					//收藏表Id
	private int isPri;				//是否是专属悬赏单 0 否 1是
	
	private PmTopicPsProducts fristObj;
	private List<PmTopicPsProducts> allList;
	private List<PmProductLabel> labelList;
	
	
	public Long getJoinId() {
		return joinId;
	}
	public void setJoinId(Long joinId) {
		this.joinId = joinId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public int getTotalPraise() {
		return totalPraise;
	}
	public void setTotalPraise(int totalPraise) {
		this.totalPraise = totalPraise;
	}
	public int getTotalDiscuss() {
		return totalDiscuss;
	}
	public void setTotalDiscuss(int totalDiscuss) {
		this.totalDiscuss = totalDiscuss;
	}
	public int getTotalShare() {
		return totalShare;
	}
	public void setTotalShare(int totalShare) {
		this.totalShare = totalShare;
	}
	public int getTotalProduct() {
		return totalProduct;
	}
	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getIsGet() {
		return isGet;
	}
	public void setIsGet(String isGet) {
		this.isGet = isGet;
	}
	public int getIsZ() {
		return isZ;
	}
	public void setIsZ(int isZ) {
		this.isZ = isZ;
	}
	public PmTopicPsProducts getFristObj() {
		return fristObj;
	}
	public void setFristObj(PmTopicPsProducts fristObj) {
		this.fristObj = fristObj;
	}
	public List<PmTopicPsProducts> getAllList() {
		return allList;
	}
	public void setAllList(List<PmTopicPsProducts> allList) {
		this.allList = allList;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<PmProductLabel> getLabelList() {
		return labelList;
	}
	public void setLabelList(List<PmProductLabel> labelList) {
		this.labelList = labelList;
	}
	public Long getCollectId() {
		return collectId;
	}
	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}
	public int getIsPri() {
		return isPri;
	}
	public void setIsPri(int isPri) {
		this.isPri = isPri;
	}
}
