package cn.temobi.core.util;

public class LevelUtil {

	public static int getLevel(int a){
		double b = a/100.0;
		double n=Math.sqrt(b);
		return (int)Math.floor(n);
	}
}
