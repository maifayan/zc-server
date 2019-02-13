package com.yale.zc.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

	/**
	 * 对字符串sha1加密
	 * @param src
	 * @return
	 */
	public static String SHA1(String src) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.update(src.getBytes());
	        return new BigInteger(1, sha1.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
