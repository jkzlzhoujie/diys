package cn.temobi.complex.entity;

/**
 */
@SuppressWarnings("serial")
public class PmTopicPsProducts extends IdEntity{
	private Long topicId;
	private Long joinId;
	private int journalSeq;
	private Long srcProductId;
	private String srcUrl;
	private String srcThumbnail;
	private Long productId;
	private String psDescription;
	private String psTime;
	private Long psUserId;
	private int psUserType;
	private int praiseCount;
	private int downloadCount;
	private int searchCount;
	private int discussCount;
	private int shareCount;
	private int stampCount;
	private int hotScore;
	private int magicScore;
	private String url;
	private String thumbnail;
	private String nickName;
	private String headImageUrl;
	private String isGet = "0";
	private double price;
	private int isZ;
	private int isC;
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
	public Long getSrcProductId() {
		return srcProductId;
	}
	public void setSrcProductId(Long srcProductId) {
		this.srcProductId = srcProductId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getPsDescription() {
		return psDescription;
	}
	public void setPsDescription(String psDescription) {
		this.psDescription = psDescription;
	}
	public String getPsTime() {
		return psTime;
	}
	public void setPsTime(String psTime) {
		this.psTime = psTime;
	}
	public Long getPsUserId() {
		return psUserId;
	}
	public void setPsUserId(Long psUserId) {
		this.psUserId = psUserId;
	}
	public int getPsUserType() {
		return psUserType;
	}
	public void setPsUserType(int psUserType) {
		this.psUserType = psUserType;
	}
	public Long getJoinId() {
		return joinId;
	}
	public void setJoinId(Long joinId) {
		this.joinId = joinId;
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
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	public String getSrcUrl() {
		return srcUrl;
	}
	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}
	public String getSrcThumbnail() {
		return srcThumbnail;
	}
	public void setSrcThumbnail(String srcThumbnail) {
		this.srcThumbnail = srcThumbnail;
	}
}
