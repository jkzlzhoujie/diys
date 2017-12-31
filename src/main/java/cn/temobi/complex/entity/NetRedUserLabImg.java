package cn.temobi.complex.entity;

public class NetRedUserLabImg extends IdEntity{
    /**
     * 
     */
    private static final long serialVersionUID = -5750426320743524576L;
    private long userId;
    private String lableName;
    private String image;
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getLableName() {
        return lableName;
    }
    public void setLableName(String lableName) {
        this.lableName = lableName;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    
}
