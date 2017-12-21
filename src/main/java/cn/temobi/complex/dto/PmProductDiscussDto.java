package cn.temobi.complex.dto;

import java.util.List;

public class PmProductDiscussDto{

	private Long discussId;
	private Long productId;
	private Long userId;
	private Long discussUserId;
	private int type;
	private String content;
	private String imageUrl;
	private String thumbnail;
	private String nickName;
	private String headImageUrl;
	private Long parentDiscussId;
	private int praises;
	private int replys;
	private int isZ;
	private String createdWhen;
	private List<TwoDiscussDto> list; 
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
	public Long getDiscussUserId() {
		return discussUserId;
	}
	public void setDiscussUserId(Long discussUserId) {
		this.discussUserId = discussUserId;
	}
	public Long getDiscussId() {
		return discussId;
	}
	public void setDiscussId(Long discussId) {
		this.discussId = discussId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getParentDiscussId() {
		return parentDiscussId;
	}
	public void setParentDiscussId(Long parentDiscussId) {
		this.parentDiscussId = parentDiscussId;
	}
	public int getPraises() {
		return praises;
	}
	public void setPraises(int praises) {
		this.praises = praises;
	}
	public int getReplys() {
		return replys;
	}
	public void setReplys(int replys) {
		this.replys = replys;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public List<TwoDiscussDto> getList() {
		return list;
	}
	public void setList(List<TwoDiscussDto> list) {
		this.list = list;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
