package cn.temobi.core.util;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * @author salim
 * @created 2012-4-1
 * @package com.plaminfo.core.util
 */
public class RandomUtils {

	private static char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '!', '@', '#', '$', '%', '^', '&', '*', '~', '|' };
	/**
	 * 竞拍随机数
	 */
	private static String[] randomNumbers = { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12", "13", "2", "13", "2", "13", "1",
			"2", "12", "1", "13", "3", "1", "13", "12", "13", "13", "1", "2",
			"13" };

	private static String[] randomTimes = { "100", "200", "300", "400", "500",
			"600", "700", "800", "800", "800", "800" };

	/**
	 * 获得随机字符
	 * 
	 * @param type
	 *            1： 表示取前10 个数字，为纯数字，2：只取26个字符 3:表示数字及字符 4：表示随机所有
	 * @param length
	 *            字符长度
	 * @param count
	 *            字符组数
	 * @return
	 */
	public static String[] getRandomStr(int type, int length, int count) {
		String[] passwords = new String[count];
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			StringBuilder password = new StringBuilder("");
			for (int m = 1; m <= length; m++) {
				if (type == 1)
					password.append(chars[random.nextInt(10)]);
				else if (type == 2)
					password.append(chars[random.nextInt(52) + 10]);
				else if (type == 3)
					password.append(chars[random.nextInt(62)]);
				else
					password.append(chars[random.nextInt(chars.length)]);
			}
			passwords[i] = StringUtils.trim(password.toString());
		}
		return passwords;
	}
	
	public static String getRandomStr(int length) {
		Random random = new Random();
		StringBuilder password = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			password.append(chars[random.nextInt(10)]);
		}
		return password.toString();
	}

	public static String getRandomvalue(int type, int length, int count) {
		String[] str = getRandomStr(type, length, count);
		StringBuffer stringBuffer = new StringBuffer("");
		for (String s : str) {
			stringBuffer.append(s.toString());
			if (count > 1)
				stringBuffer.append(";");
		}
		return stringBuffer.toString();
	}

	public static String getRandNumberStr(int num) {
		Random random = new Random();
		String numStr = "";
		try {
			numStr = randomNumbers[random.nextInt(num)];
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(numStr))
			numStr = String.valueOf(random.nextInt(10));
		return numStr;
	}

	public static int getRandNumberByAuctionTimes(int ranNum) {
		return Integer.valueOf(getRandNumberStr(ranNum));
	}

	public static long getRandAuctionTimes(int ranNum, int ranTimeNum) {
		int randNumber = getRandNumberByAuctionTimes(ranNum);
		int times = 0;
		if (randNumber >= 12) {
			Random random = new Random();
			times = Integer.valueOf(randomTimes[random.nextInt(ranTimeNum)]);
		}
		return randNumber * 1000 + times;
	}

}
