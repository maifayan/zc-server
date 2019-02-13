package com.yale.zc.weixin.web;

import com.yale.zc.weixin.service.WeixinService;
import com.yale.zc.weixin.vo.WeixinSignVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 微信授权认证业务

 *
 */

@RestController
@RequestMapping(value = "/weixin")
@CrossOrigin
public class WeixinController {
	
	@Autowired
    WeixinService weixinService;
	
	/**
	 * 微信OAuth2.0认证
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/auth")
	public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {

		weixinService.auth(request,response);
	}
	
	/**
	 * 微信认证回调
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/callback")
	public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// System.out.println(request.getRequestURI());
		weixinService.callback(request, response);
	}
	
	/**
	 * 接收微信配置请求
	 * @param vo
	 * @return echostr
	 */
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	@ResponseBody
	public String config(WeixinSignVo vo) {
		return weixinService.config(vo);
	}


	/*
	 *接收微信分享配置请求
	 */
	@RequestMapping("/wxShareConfig")
	public Map<String, Object> wxShareConfig(ServletRequest request){
		return weixinService.wxShareConfig(request);
	}


}






