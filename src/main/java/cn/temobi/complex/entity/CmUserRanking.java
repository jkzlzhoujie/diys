package cn.temobi.complex.entity;

public class CmUserRanking extends IdEntity{
	
	private Long userId;
	private String nickName;
	private String headImageUrl;
	private String addTime;
	private int charm;
	private int originality;
	private int totalScore;
	private int charmNum;
	private int originalityNum;
	private int totalScoreNum;
	private int yesCharmNum;
	private int yesOriginalityNum;
	private int yesTotalScoreNum;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public int getCharm() {
		return charm;
	}
	public void setCharm(int charm) {
		this.charm = charm;
	}
	public int getOriginality() {
		return originality;
	}
	public void setOriginality(int originality) {
		this.originality = originality;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getCharmNum() {
		return charmNum;
	}
	public void setCharmNum(int charmNum) {
		this.charmNum = charmNum;
	}
	public int getOriginalityNum() {
		return originalityNum;
	}
	public void setOriginalityNum(int originalityNum) {
		this.originalityNum = originalityNum;
	}
	public int getTotalScoreNum() {
		return totalScoreNum;
	}
	public void setTotalScoreNum(int totalScoreNum) {
		this.totalScoreNum = totalScoreNum;
	}
	public int getYesCharmNum() {
		return yesCharmNum;
	}
	public void setYesCharmNum(int yesCharmNum) {
		this.yesCharmNum = yesCharmNum;
	}
	public int getYesOriginalityNum() {
		return yesOriginalityNum;
	}
	public void setYesOriginalityNum(int yesOriginalityNum) {
		this.yesOriginalityNum = yesOriginalityNum;
	}
	public int getYesTotalScoreNum() {
		return yesTotalScoreNum;
	}
	public void setYesTotalScoreNum(int yesTotalScoreNum) {
		this.yesTotalScoreNum = yesTotalScoreNum;
	}
}
