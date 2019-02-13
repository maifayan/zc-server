package com.yale.zc.core.util;

import java.util.Arrays;

public class WeixinUtil {
	private static final String TOKEN = "zc_2018";
//	private static final String APPID = "wxede20d9286db6756";
//	private static final String APPSECRET = "b548de5f324a3abc9cab2d9d32c49e15";
//	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
//	private static final String UPLOAD_TEMPRORY_METERIAL_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
//	private static final String UPLOAD_METERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
//	private static final String UPLOAD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
//	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	
	/**
	 * 校验微信配置参数
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		
		String[] arr = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(arr);
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}

		String localSignature = SecurityUtil.SHA1(content.toString());

		return localSignature.equals(signature);
	}

	/**
	 * 签名
	 * @param ticket
	 * @param timestamp
	 * @param nonceStr
	 * @param url
	 * @return
	 */
	public static String signature(String ticket, long timestamp, String nonceStr, String url) {
		String signature = null;
		 String str = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;

		signature = SecurityUtil.SHA1(str);
		return signature;
	}
	
}
