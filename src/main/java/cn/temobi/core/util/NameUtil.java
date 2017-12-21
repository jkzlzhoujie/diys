package cn.temobi.core.util;


public class NameUtil {

	public static void main(String[] args) {
		for(int i=0;i<100;i++)
		{	
			System.out.println(getRanName());
		}
	}
	
	public static String getRanName()
	{
		String yName = PropertiesHelper.getProperty("properties/name.properties", "yName");
		String yX = PropertiesHelper.getProperty("properties/name.properties", "yX");
		String zName = PropertiesHelper.getProperty("properties/name.properties", "zName");
		String zX = PropertiesHelper.getProperty("properties/name.properties", "zX");
		
		int a = (int) (Math.random()*2);
		String name="";
		if(a==1)
		{
			String[] yNameArr = yName.split(",");
			String[] yXArr = yX.split(",");
			int yNameInt = (int) (Math.random()*yNameArr.length);
			int yXInt = (int) (Math.random()*yXArr.length);
			name = yNameArr[yNameInt]+yXArr[yXInt];
		}else{
			String[] zNameArr = zName.split(",");
			String[] zXArr = zX.split(",");
			int zNameInt = (int) (Math.random()*zNameArr.length);
			int zXInt = (int) (Math.random()*zXArr.length);
			name = zNameArr[zNameInt]+zXArr[zXInt];
		}
		
		return name;
	}
}
