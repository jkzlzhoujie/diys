package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EmojiItemsDto implements Serializable {
	private Long emotionId;//表情编号
	private String emotionStatic;//表情静态图
	public Long getEmotionId() {
		return emotionId;
	}
	public void setEmotionId(Long emotionId) {
		this.emotionId = emotionId;
	}
	public String getEmotionStatic() {
		return emotionStatic;
	}
	public void setEmotionStatic(String emotionStatic) {
		this.emotionStatic = emotionStatic;
	}
	
}
