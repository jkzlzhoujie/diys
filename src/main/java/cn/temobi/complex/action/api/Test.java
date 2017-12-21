package cn.temobi.complex.action.api;

import java.util.ArrayList;
import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		
		 System.out.println("demandPIndex@page"+1 );
		
		String urlStr = "htttp:ss";
		String s = "s";
		
		if(urlStr.indexOf(s) != -1){
			 System.out.println("你输入的内容含有敏感词");
		}
		
		List<String> strList = new ArrayList<String>();
		
		strList.add("1");
		strList.add("2");
		strList.add("3");
		strList.add("4");
		
//		for (int i = 0; i < 8; i++) {
//			System.out.println(strList.size());
//			strList.remove(String.valueOf(i));
//		}
		
		
	}

}
