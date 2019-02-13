package com.yale.zc.core.util;

import java.util.Random;

public class RandomUtil {

	private static Random random = new Random();
	
	public static String getRandom() {
		RandomUtil ru = new RandomUtil();
		String s = "";

		for (int i = 0; i < 4; i++) {
			s += ru.matchNumber();

		}
		return s;
	}

	// 随机数字的字符串
	private String matchNumber() {
		// 数字48-57(0-9)
		int num = random.nextInt(10) + 48;
		return String.valueOf((char)num);
	}
	
	public static void main(String[] args) {

	}
}

